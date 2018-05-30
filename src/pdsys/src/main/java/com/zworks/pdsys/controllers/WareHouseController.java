package com.zworks.pdsys.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.business.beans.WareHouseListFormBean;
import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
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
			type = RequestContextUtils.getSessionAttribute(this, "his_type", "pn");
		}
		RequestContextUtils.setSessionAttribute(this, "his_type", type);
		
		Date s = DateUtils.startOfDay(formBean.getStart());
		Date e = DateUtils.endOfDay(formBean.getEnd());
		if(s == null) {
			s = RequestContextUtils.getSessionAttribute(this, "start" + type, DateUtils.startOfDay(DateUtils.thisMonthStart()));
			formBean.setStart(s);
		}
		RequestContextUtils.setSessionAttribute(this, "start" + type, s);
		if(e == null) {
			e = RequestContextUtils.getSessionAttribute(this, "end" + type, DateUtils.endOfDay(DateUtils.now()));
			formBean.setEnd(e);
		}
		RequestContextUtils.setSessionAttribute(this, "end" + type, e);
		
		if(type.equals("deliverybom")) {
			WareHouseDeliveryBOMModel bom = new WareHouseDeliveryBOMModel();
			WareHouseDeliveryModel d = new WareHouseDeliveryModel();
			d.setState(DeliveryState.DELIVERIED.ordinal());
			bom.setWareHouseDelivery(d);
			bom.getFilterCond().put("deliveryStart", s);
			bom.getFilterCond().put("deliveryEnd", e);
			bom.getFilterCond().put("groupByBOM", true);
			
			List<WareHouseDeliveryBOMModel> list = wareHouseDeliveryBOMService.queryList(bom);
			model.addAttribute("list", list);
		} else if(type.equals("entrybom")) {
			WareHouseEntryBOMModel bom = new WareHouseEntryBOMModel();
			WareHouseEntryModel entry = new WareHouseEntryModel();
			entry.setState(EntryState.ENTRIED.ordinal());
			bom.setWareHouseEntry(entry);
			bom.getFilterCond().put("entryStart", s);
			bom.getFilterCond().put("entryEnd", e);
			bom.getFilterCond().put("groupByBOM", true);
			
			List<WareHouseEntryBOMModel> list = wareHouseEntryBOMService.queryList(bom);
			model.addAttribute("list", list);
		}
		else if(type.equals("pn")) {
			WareHouseDeliveryPnModel pn = new WareHouseDeliveryPnModel();
			WareHouseDeliveryModel d = new WareHouseDeliveryModel();
			d.setState(DeliveryState.DELIVERIED.ordinal());
			pn.setWareHouseDelivery(d);
			pn.getFilterCond().put("deliveryStart", s);
			pn.getFilterCond().put("deliveryEnd", e);
			pn.getFilterCond().put("groupByPn", true);
			
			List<WareHouseDeliveryPnModel> list = wareHouseDeliveryPnService.queryList(pn);
			model.addAttribute("list", list);
		} else {
			throw new PdsysException("错误参数:/history/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		
		model.addAttribute("formBean", formBean);
		model.addAttribute("type", type);
		return "warehouse/history/main";
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
