package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.services.WareHouseEntryBOMService;
import com.zworks.pdsys.services.WareHouseEntryMachinePartService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
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
	WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	WareHouseEntryMachinePartService wareHouseEntryMachinePartService;

	@RequestMapping(value= {"/main", "/main/{type}"})
    public String entryMain(
    		@PathVariable(name="type" ,required=false)String type,
    		@RequestParam(name="no", required=false)String no,
    		Model model) {

		if(type == null) {
			type = RequestContextUtils.getSessionAttribute(this, "type", "pn");
		}
		else if(!(type.equals("bom") || type.equals("pn") || type.equals("machinepart"))) {
			throw new PdsysException("错误参数:/entry/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		RequestContextUtils.setSessionAttribute(this, "type", type);
		
		if(no == null) {
			no = RequestContextUtils.getSessionAttribute(this, "no" + type, null);
		}
		RequestContextUtils.setSessionAttribute(this, "no" + type, no);

		WareHouseEntryModel entry = null;
		if(no != null) {
			entry = new WareHouseEntryModel();
			entry.setNo(no);
			entry.getFilterCond().put("fuzzyNoSearch", true);
			
			if(type.equals("bom")) {
				entry = wareHouseEntryService.queryOneWithBOM(entry);
			} else if(type.equals("pn")) {
				entry = wareHouseEntryService.queryOneWithPn(entry);
			} else if(type.equals("machinepart")) {
				entry = wareHouseEntryService.queryOneWithMachinePart(entry);
			} else {
				entry = null;
			}
		}

		if(entry == null) {
			entry = new WareHouseEntryModel();
		}
		
		model.addAttribute("entry", entry);
		model.addAttribute("type", type);
		return "warehouse/entry/main";
    }
	
	/**
	 * 新建入库单
	 * */
	@RequestMapping(value="/add/entry")
	@ResponseBody
    public JSONResponse addEntry(@RequestBody WareHouseEntryModel entry, Model model) {
		WareHouseEntryModel e = new WareHouseEntryModel();
		e.setNo(entry.getNo());
		if(wareHouseEntryService.exists(e)) {
			return JSONResponse.error("已经存在单号:" + entry.getNo());
		}
		
		e = new WareHouseEntryModel();
		e.setType(entry.getType());
		e.setUser(entry.getUser());
		e.setState(EntryState.PLANNING.ordinal());
		List<WareHouseEntryModel> es = wareHouseEntryService.queryList(e);
		if(es.size()!=0) {
			return JSONResponse.error("当前用户[" + es.get(0).getUser().getName() + "]存在未处理单号:" + es.get(0).getNo());
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
		if(wareHouseEntryPnService.exist(entryPn)) {
			wareHouseEntryPnService.update(entryPn);
		} else {
			wareHouseEntryPnService.add(entryPn);
		}
		return JSONResponse.success();
    }
	
	/**
	 * 新建入库明细
	 * */
	@RequestMapping(value="/update/bom")
	@ResponseBody
    public JSONResponse updateEntryBOM(@RequestBody WareHouseEntryBOMModel entryBOM, Model model) {
		if(wareHouseEntryBOMService.exist(entryBOM)) {
			wareHouseEntryBOMService.update(entryBOM);
		} else {
			wareHouseEntryBOMService.add(entryBOM);
		}
		return JSONResponse.success();
    }
	
	/**
	 * 新建入库明细
	 * */
	@RequestMapping(value="/update/machinepart")
	@ResponseBody
    public JSONResponse updateEntryBOM(@RequestBody WareHouseEntryMachinePartModel entryMp, Model model) {
		if(wareHouseEntryMachinePartService.exist(entryMp)) {
			wareHouseEntryMachinePartService.update(entryMp);
		} else {
			wareHouseEntryMachinePartService.add(entryMp);
		}
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
		if(entry == null) {
			throw new PdsysException("错误参数:/entry/delete/entry/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM); 
		}
		wareHouseEntryService.delete(entry);
		return JSONResponse.success();
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
			throw new PdsysException("错误参数:/entry/entry/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM); 
		}
		if(wareHouseEntryService.entry(entry)) {
			return JSONResponse.success();
		} else {
			return JSONResponse.error();
		}
    }
}
