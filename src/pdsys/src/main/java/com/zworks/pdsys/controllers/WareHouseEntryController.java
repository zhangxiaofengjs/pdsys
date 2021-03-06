package com.zworks.pdsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.business.WareHouseEntryBusiness;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.enumClass.EntryType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;
import com.zworks.pdsys.services.WareHouseEntryBOMService;
import com.zworks.pdsys.services.WareHouseEntryMachinePartService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntrySemiPnService;
import com.zworks.pdsys.services.WareHouseEntryService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
@RequestMapping("/warehouse/entry")
public class WareHouseEntryController {
	@Autowired
	WareHouseEntryService wareHouseEntryService;
	@Autowired
	WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	WareHouseEntryMachinePartService wareHouseEntryMachinePartService;
	@Autowired
	WareHouseEntryBusiness wareHouseEntryBusiness;

	@RequestMapping(value= {"/main", "/main/{type}"})
    public String entryMain(
    		@PathVariable(name="type" ,required=false)String type,
    		@RequestParam(name="no", required=false)String no,
    		@RequestParam(name="state", required=false)Integer state,
    		@RequestParam(name="content", required=false)String content,
    		Model model) {

		if(type == null) {
			type = RequestContextUtils.getSessionAttribute(this, "type", "pn");
		}
		else if(!(type.equals("bom") || type.equals("pn") || type.equals("semipn") || type.equals("machinepart"))) {
			throw new PdsysException("错误参数:/entry/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		RequestContextUtils.setSessionAttribute(this, "type", type);
		
		if(no == null) {
			no = RequestContextUtils.getSessionAttribute(this, "no" + type, null);
		}
		RequestContextUtils.setSessionAttribute(this, "no" + type, no);
		
		if(state == null) {
			state = RequestContextUtils.getSessionAttribute(this, "state" + type, 0);
		}
		RequestContextUtils.setSessionAttribute(this, "state" + type, state);
		
		if(content == null) {
			content = RequestContextUtils.getSessionAttribute(this, "content" + content, "list");
		}
		RequestContextUtils.setSessionAttribute(this, "content" + content, content);

		List<?> list = null;
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setNo(no);
		entry.setState(state);
		entry.getFilterCond().put("fuzzyNoSearch", content.equals("list"));
		
		if(content.equals("list")) {
			if(type.equals("bom")) {
				entry.setType(EntryType.BOM.ordinal());
				list = wareHouseEntryService.queryList(entry);
			} else if(type.equals("pn")) {
				entry.setType(EntryType.PN.ordinal());
				list = wareHouseEntryService.queryList(entry);
			} else if(type.equals("semipn")) {
				entry.setType(EntryType.SEMIPN.ordinal());
				list = wareHouseEntryService.queryList(entry);
			} 
			else if(type.equals("machinepart")) {
				entry.setType(EntryType.MACHINEPART.ordinal());
				list = wareHouseEntryService.queryList(entry);
			} else {
				list = new ArrayList<WareHouseEntryModel>();
			}
		} else {
			if(type.equals("bom")) {
				list = wareHouseEntryService.queryListWithBOM(entry);
			} else if(type.equals("pn")) {
				list = wareHouseEntryService.queryListWithPn(entry);
			} else if(type.equals("semipn")) {
				list = wareHouseEntryService.queryListWithSemiPn(entry);
			} 
			else if(type.equals("machinepart")) {
				list = wareHouseEntryService.queryListWithMachinePart(entry);
			} else {
				list = new ArrayList<WareHouseEntryModel>();
			}
		}
		
		if(!content.equals("list") && list.size()>0) {
			entry = (WareHouseEntryModel)list.get(0);
		}
		model.addAttribute("entry", entry);
		model.addAttribute("entries", list);
		model.addAttribute("type", type);
		model.addAttribute("content", content);
		return "warehouse/entry/main";
    }
	
	/**
	 * 新建入库单
	 * */
	@RequestMapping(value="/add/entry")
	@ResponseBody
    public JSONResponse addEntry(@RequestBody WareHouseEntryModel entry, Model model) {
		try {
			wareHouseEntryService.checkAddable(entry);
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
		wareHouseEntryService.add(entry);
		return JSONResponse.success().put("entry", entry);
    }
	
	/**
	 * 新建入库明细
	 * */
	@RequestMapping(value="/update/pn")
	@ResponseBody
    public JSONResponse updateEntryPn(@RequestBody WareHouseEntryPnModel entryPn, Model model) {
		wareHouseEntryBusiness.updateEntryPn(entryPn);
		return JSONResponse.success();
    }
	
	@RequestMapping(value="/update/semipn")
	@ResponseBody
	public JSONResponse updateEntrySemiPn(@RequestBody WareHouseEntrySemiPnModel entrySemiPn, Model model) {
		wareHouseEntryBusiness.updateEntrySemiPn(entrySemiPn);
		return JSONResponse.success();
	}
	
	@RequestMapping(value="/update/bom")
	@ResponseBody
    public JSONResponse updateEntryBOM(@RequestBody WareHouseEntryBOMModel entryBOM, Model model) {
		wareHouseEntryBusiness.updateEntryBOM(entryBOM);
		return JSONResponse.success();
    }
	
	@RequestMapping(value="/update/machinepart")
	@ResponseBody
    public JSONResponse updateEntryMachinePart(@RequestBody WareHouseEntryMachinePartModel entryMp, Model model) {
		wareHouseEntryBusiness.updateEntryMachinePart(entryMp);
		return JSONResponse.success();
    }
	
	/**
	 * 删除入库明细
	 * */
	@RequestMapping(value="/delete/pn")
	@ResponseBody
    public JSONResponse deleteEntryPns(@RequestBody List<WareHouseEntryPnModel> entryPns, Model model) {
		wareHouseEntryPnService.delete(entryPns);
		return JSONResponse.success();
    }
	
	@RequestMapping(value="/delete/semipn")
	@ResponseBody
	public JSONResponse deleteEntrySemiPns(@RequestBody List<WareHouseEntrySemiPnModel> entryPns, Model model) {
		wareHouseEntrySemiPnService.delete(entryPns);
		return JSONResponse.success();
	}
	
	/**
	 * 删除入库明细
	 * */
	@RequestMapping(value="/delete/bom")
	@ResponseBody
	public JSONResponse deleteEntryBOMs(@RequestBody List<WareHouseEntryBOMModel> entryBOMs, Model model) {
		wareHouseEntryBOMService.delete(entryBOMs);
		return JSONResponse.success();
	}
	
	/**
	 * 删除入库明细
	 * */
	@RequestMapping(value="/delete/machinepart")
	@ResponseBody
	public JSONResponse deleteEntryMachineParts(@RequestBody List<WareHouseEntryMachinePartModel> entryMachineParts, Model model) {
		wareHouseEntryMachinePartService.delete(entryMachineParts);
		return JSONResponse.success();
	}
	
	/**
	 * 删除入库单
	 * */
	@RequestMapping(value="/delete/entry/{id}")
	@ResponseBody
	public JSONResponse deleteEntry(@PathVariable(name="id") Integer id, Model model) {
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setId(id);
		entry = wareHouseEntryService.queryOne(entry);
		if(entry.getState() != EntryState.PLANNING.ordinal()) {
			return JSONResponse.error("只能删除计划中入库单");
		}
		
		if(!SecurityContextUtils.isLoginUser(entry.getUser())) {
			return JSONResponse.error("当前用户不是提交者");
		}
		
		wareHouseEntryService.delete(entry);
		return JSONResponse.success();
	}
	
	/**
	 * 导入入库单
	 * */
	@RequestMapping(value="/import/entry")
	@ResponseBody
	public JSONResponse importEntry(@RequestParam("file") MultipartFile[] files) {
		try {
			wareHouseEntryService.importEntry(files);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	/**
	 * 入库
	 * */
	@RequestMapping(value="/entry/{id}")
	@ResponseBody
    public JSONResponse entry(@PathVariable(name="id", required=false)Integer id, Model model) {
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setId(id);
		entry = wareHouseEntryService.queryOne(entry);
		if(entry == null) {
			return JSONResponse.error("不存在单号,请刷新页面。");
		}
		if(!SecurityContextUtils.isLoginUser(entry.getUser())) {
			return JSONResponse.error("当前用户不是提交者");
		}
		
		try {
			if(entry.getType() == EntryType.PN.ordinal()) {
				wareHouseEntryBusiness.entryPn(entry);
			} else if(entry.getType() == EntryType.SEMIPN.ordinal()) {
				wareHouseEntryBusiness.entrySemiPn(entry);
			} else if(entry.getType() == EntryType.BOM.ordinal()) {
				wareHouseEntryBusiness.entryBOM(entry);
			} else if(entry.getType() == EntryType.MACHINEPART.ordinal()) {
				wareHouseEntryBusiness.entryMachinePart(entry);
			} else {
				throw new PdsysException("未想定入库种类");
			}
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
    }
}
