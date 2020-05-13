package com.zworks.pdsys.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.business.WareHouseBusiness;
import com.zworks.pdsys.business.WareHouseDeliveryBusiness;
import com.zworks.pdsys.business.WareHouseEntryBusiness;
import com.zworks.pdsys.business.form.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.business.form.beans.WareHouseListFormBean;
import com.zworks.pdsys.common.annotations.PdSysSessionValue;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.io.WareHouseHistoryWriter;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.services.FileService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseEntryBOMService;
import com.zworks.pdsys.services.WareHouseMachinePartService;
import com.zworks.pdsys.services.WareHousePnService;
import com.zworks.pdsys.services.WareHouseSemiPnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
@RequestMapping("/warehouse")
public class WareHouseController extends BaseController{
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	@Autowired
	WareHouseBusiness wareHouseBusiness;
	@Autowired
	WareHousePnService wareHousePnService;
	@Autowired
	WareHouseSemiPnService wareHouseSemiPnService;
	@Autowired
	WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseEntryBusiness wareHouseEntryBusiness;
	@Autowired
	WareHouseDeliveryBusiness wareHouseDeliveryBusiness;
	@Autowired
	WareHouseHistoryWriter wareHouseHistoryWriter;
	@Autowired
	FileService fileService;

	@RequestMapping("/list/main")
	@PdSysSessionValue(name="formBean", defaultValue=PdSysSessionValue.VALUE_INSTANCE)
    public String listMain(WareHouseListFormBean formBean, Model model) {
		if("bom".equals(formBean.getType())) {
			List<?> list = wareHouseBOMService.queryListByPn(formBean.getBomPn(), true);
			model.addAttribute("list", list);
		}
		else if("pn".equals(formBean.getType())) {
			List<WareHousePnModel> list = wareHousePnService.queryListByPn(formBean.getPnPn(), true);
			model.addAttribute("list", list);
		}
		else if("semipn".equals(formBean.getType())) {
			List<WareHouseSemiPnModel> list = wareHouseSemiPnService.queryListByPn(formBean.getSemipnPn(), true);
			model.addAttribute("list", wareHouseSemiPnService.convertToSemiPnList(list));
		}
		else if("machinepart".equals(formBean.getType())) {
			List<?> list = wareHouseMachinePartService.queryListByPn(formBean.getMachinePartPn(), true);
			model.addAttribute("list", list);
		}
		else {
			throw new PdsysException("错误参数:/list/main?type=" + formBean.getType(), PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
				
		model.addAttribute("formBean", formBean);
		return "warehouse/list/main";
    }
	
	@RequestMapping(value= {"/history/main", "/history/main/{type}"})
	public String historyMain(
			@PathVariable(name="type" ,required=false)String type,
			WareHouseHistoryFormBean formBean,
			Model model) {
		if(type == null) {
			type = RequestContextUtils.getSessionAttribute(this, "his_type", "entrypn");
		}
		RequestContextUtils.setSessionAttribute(this, "his_type", type);
		
		Date s = DateUtils.startOfDay(formBean.getStart());
		Date e = DateUtils.endOfDay(formBean.getEnd());
		String pn = formBean.getPn();
		int bomType = formBean.getBomType();
		if(s == null) {
			s = RequestContextUtils.getSessionAttribute(this, "start" + type, DateUtils.startOfDay(DateUtils.getCurrentDate()));
			formBean.setStart(s);
		}
		RequestContextUtils.setSessionAttribute(this, "start" + type, s);
		if(e == null) {
			e = RequestContextUtils.getSessionAttribute(this, "end" + type, DateUtils.endOfDay(DateUtils.now()));
			formBean.setEnd(e);
		}
		RequestContextUtils.setSessionAttribute(this, "end" + type, e);
		if(pn == null) {
			pn= RequestContextUtils.getSessionAttribute(this, "pn" + type, "");
			formBean.setPn(pn);
		}
		RequestContextUtils.setSessionAttribute(this, "pn" + type, pn);
		if(bomType == -1) {
			bomType= RequestContextUtils.getSessionAttribute(this, "bomType" + type, -1);
			formBean.setBomType(bomType);
		}
		RequestContextUtils.setSessionAttribute(this, "bomType" + type, bomType);

		if(type.equals("deliverybom")) {
			List<WareHouseDeliveryBOMModel> list = wareHouseDeliveryBusiness.calcDeliveryBOMs(formBean);
			model.addAttribute("list", list);
		} else if(type.equals("entrybom")) {
			List<WareHouseEntryBOMModel> list = wareHouseEntryBusiness.calcEntryBOMs(formBean);
			model.addAttribute("list", list);
		} else if(type.equals("entrypn")) {
			List<WareHouseEntryPnModel> list = wareHouseEntryBusiness.calcEntryPns(formBean);
			model.addAttribute("list", list);
		} else if(type.equals("deliverypn")) {
			List<WareHouseDeliveryPnModel> list = wareHouseDeliveryBusiness.calcDeliveryPns(formBean);
			model.addAttribute("list", list);
		} else {
			throw new PdsysException("错误参数:/history/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		
		model.addAttribute("formBean", formBean);
		model.addAttribute("type", type);
		return "warehouse/history/main";
	}
	
	@RequestMapping(value= {"/history/download/{type}"})
	public ResponseEntity<byte[]> historyDownload(
			@PathVariable(name="type" ,required=false)String type,
			WareHouseHistoryFormBean formBean,
			Model model) {
		
		String filePath = String.format("[%s](%s-%s_%s_%s).xlsx",
				DateUtils.format(DateUtils.getCurrentDate(),"yyyyMMdd"),
				formBean.getStart()==null?"":DateUtils.format(formBean.getStart(),"yyyyMMdd"),
				formBean.getEnd()==null?"":DateUtils.format(formBean.getEnd(),"yyyyMMdd"),
				formBean.getPn(),
				formBean.getBomType());
		
		if(type.equals("deliverybom")) {
			List<WareHouseDeliveryBOMModel> list = wareHouseDeliveryBusiness.calcDeliveryBOMs(formBean);
			filePath = fileService.getTempFilePath("原包材出库统计" + filePath);
			wareHouseHistoryWriter.writeDeliveryBOM(list, fileService.getTempFilePath(filePath));
		} else if(type.equals("entrybom")) {
			List<WareHouseEntryBOMModel> list = wareHouseEntryBusiness.calcEntryBOMs(formBean);
			filePath = fileService.getTempFilePath("原包材入库统计" + filePath);
			wareHouseHistoryWriter.writeEntryBOM(list, fileService.getTempFilePath(filePath));
		} else if(type.equals("entrypn")) {
			List<WareHouseEntryPnModel> list = wareHouseEntryBusiness.calcEntryPns(formBean);
			filePath = fileService.getTempFilePath("生产入库统计" + filePath);
			wareHouseHistoryWriter.writeEntryPn(list, fileService.getTempFilePath(filePath));
		} else if(type.equals("deliverypn")) {
			List<WareHouseDeliveryPnModel> list = wareHouseDeliveryBusiness.calcDeliveryPns(formBean);
			filePath = fileService.getTempFilePath("生产出库统计" + filePath);
			wareHouseHistoryWriter.writeDeliveryPn(list, fileService.getTempFilePath(filePath));
		} else {
			throw new PdsysException("错误参数:/history/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}

		return fileService.download(filePath);
	}
	@RequestMapping("/list/pn/json")
	@ResponseBody
	public JSONResponse listPnJson(@RequestBody WareHousePnModel pn, Model model) {
		List<WareHousePnModel> list = wareHousePnService.queryList(pn);
		return JSONResponse.success().put("pns", list);
	}
	
	@RequestMapping("/list/semipn/json")
	@ResponseBody
    public JSONResponse listSemiJson(@RequestBody WareHouseSemiPnModel pn, Model model) {
		List<WareHouseSemiPnModel> list = wareHouseSemiPnService.queryList(pn);
        return JSONResponse.success().put("semipns", list);
    }
	
	@RequestMapping("/list/bom/json")
	@ResponseBody
    public JSONResponse listBOMJson(@RequestBody WareHouseBOMModel bom, Model model) {
		List<WareHouseBOMModel> list = wareHouseBOMService.queryList(bom);
        return JSONResponse.success().put("warehouseboms", list);
    }
	
	@RequestMapping("/list/machinepart/json")
	@ResponseBody
    public JSONResponse listMPJson(@RequestBody WareHouseMachinePartModel mp, Model model) {
		List<WareHouseMachinePartModel> list = wareHouseMachinePartService.queryList(mp);
        return JSONResponse.success().put("machineparts", list);
    }
	
	@RequestMapping("/update/pn")
	@ResponseBody
	public JSONResponse updatePn(@RequestBody WareHousePnModel pn, Model model) {
		try {
			wareHousePnService.update(pn);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/import/bom")
	@ResponseBody
	public JSONResponse importBOM(@RequestParam("file") MultipartFile[] files) {
		try {
			wareHouseBusiness.importBOM(files);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/import/pn")
	@ResponseBody
	public JSONResponse importPn(@RequestParam("file") MultipartFile[] files) {
		try {
			wareHouseBusiness.importPn(files);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/import/semipn")
	@ResponseBody
	public JSONResponse importSemiPn(@RequestParam("file") MultipartFile[] files) {
		try {
			wareHouseBusiness.importSemiPn(files);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
}
