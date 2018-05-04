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

import com.zworks.pdsys.business.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.business.beans.WareHouseListFormBean;
import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseMachinePartService;
import com.zworks.pdsys.services.WareHousePnService;

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
	WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	
	@RequestMapping("/list/main")
    public String listMain(@RequestParam(name="type",required = false, defaultValue="pn")String type, 
    		WareHouseListFormBean formBean,
    		Model model) {
		if(formBean == null) {
			formBean = new WareHouseListFormBean();
		}
		
		if(type.equals("bom")) {
			BOMModel bom = formBean.getBOM();
			if(bom != null) {
				bom.getFilterCond().put("fuzzyPnSearch", true);
			}
			List<?> list = wareHouseBOMService.queryList(bom);
			model.addAttribute("list", list);
		}
		else if(type.equals("pn")) {
			WareHousePnModel whPn = formBean.getWareHousePn();
			if(whPn != null) {
				whPn.getFilterCond().put("fuzzyPnSearch", true);
			}
			List<?> list = wareHousePnService.queryList(whPn);
			model.addAttribute("list", list);
		}
		else if(type.equals("machinepart")) {
			WareHouseMachinePartModel whMp = formBean.getWareHouseMachinePart();
			if(whMp != null) {
				whMp.getFilterCond().put("fuzzyPnSearch", true);
			}
			List<?> list = wareHouseMachinePartService.queryList(whMp);
			model.addAttribute("list", list);
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
		if(formBean == null) {
			formBean = new WareHouseHistoryFormBean();
		}
		formBean.normalizeStartEnd();
		if(type == null) {
			type = "bom";
		}
		
		if(type.equals("bom")) {
			WareHouseDeliveryBOMModel bom = new WareHouseDeliveryBOMModel();
			WareHouseDeliveryModel d = new WareHouseDeliveryModel();
			d.setState(DeliveryState.DELIVERIED.ordinal());
			bom.setWareHouseDelivery(d);
			bom.getFilterCond().put("deliveryStart", formBean.getStart());
			bom.getFilterCond().put("deliveryEnd", formBean.getEnd());
			List<WareHouseDeliveryBOMModel> list = wareHouseDeliveryBOMService.queryList(bom);
			model.addAttribute("list", list);
		}
		else if(type.equals("pn")) {
			List<UserModel> list = new ArrayList<UserModel>();
			model.addAttribute("list", list);
		} else {
			throw new PdsysException("错误参数:/history/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		
		model.addAttribute("formBean", formBean);
		model.addAttribute("type", type);
		return "warehouse/history/main";
	}
}
