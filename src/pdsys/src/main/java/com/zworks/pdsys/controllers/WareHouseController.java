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

import com.zworks.pdsys.business.WareHouseDeliveryBusiness;
import com.zworks.pdsys.business.WareHouseEntryBusiness;
import com.zworks.pdsys.business.form.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.business.form.beans.WareHouseListFormBean;
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
    public String listMain(@RequestParam(name="type",required = false/*, defaultValue="pn"*/)String type, 
    		WareHouseListFormBean formBean,
    		Model model) {
		if(type == null) {
			type = RequestContextUtils.getSessionAttribute(this, "type", "pn");
		}
		RequestContextUtils.setSessionAttribute(this, "type", type);
		
		if(type.equals("bom")) {
			WareHouseBOMModel whbom = formBean.getWareHouseBOM();
			if(whbom == null) {
				whbom =  RequestContextUtils.getSessionAttribute(this, "whbom", new WareHouseBOMModel());
				formBean.setWareHouseBOM(whbom);
			}
			whbom.getFilterCond().put("fuzzyPnSearch", true);
			
			List<?> list = wareHouseBOMService.queryList(whbom);
			model.addAttribute("list", list);
			RequestContextUtils.setSessionAttribute(this, "whbom", whbom);
		}
		else if(type.equals("pn")) {
			WareHousePnModel whPn = formBean.getWareHousePn();
			if(whPn == null) {
				whPn =  RequestContextUtils.getSessionAttribute(this, "whPn", new WareHousePnModel());
				formBean.setWareHousePn(whPn);
			}
			whPn.getFilterCond().put("fuzzyPnSearch", true);
			
			List<?> list = wareHousePnService.queryList(whPn);
			model.addAttribute("list", list);
			RequestContextUtils.setSessionAttribute(this, "whPn", whPn);
		}
		else if(type.equals("semipn")) {
			WareHouseSemiPnModel whPn = formBean.getWareHouseSemiPn();
			if(whPn == null) {
				whPn =  RequestContextUtils.getSessionAttribute(this, "whSemiPn", new WareHouseSemiPnModel());
				formBean.setWareHouseSemiPn(whPn);
			}
			whPn.getFilterCond().put("fuzzyPnSearch", true);
			
			List<WareHouseSemiPnModel> list = wareHouseSemiPnService.queryList(whPn);
			model.addAttribute("list", wareHouseSemiPnService.convertToSemiPnList(list));
			RequestContextUtils.setSessionAttribute(this, "whSemiPn", whPn);
		}
		else if(type.equals("machinepart")) {
			WareHouseMachinePartModel whMp = formBean.getWareHouseMachinePart();
			if(whMp == null) {
				whMp =  RequestContextUtils.getSessionAttribute(this, "whMp", new WareHouseMachinePartModel());
				formBean.setWareHouseMachinePart(whMp);
			}
			whMp.getFilterCond().put("fuzzyPnSearch", true);
			List<?> list = wareHouseMachinePartService.queryList(whMp);
			model.addAttribute("list", list);
			RequestContextUtils.setSessionAttribute(this, "whMp", whMp);
		}
		else {
			throw new PdsysException("错误参数:/list/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
				
		model.addAttribute("formBean", formBean);
		model.addAttribute("type", type);
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
}
