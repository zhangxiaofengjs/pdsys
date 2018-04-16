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

import com.zworks.pdsys.business.beans.WareHouseAddDeliveryObjFormBean;
import com.zworks.pdsys.business.beans.WareHouseListFormBean;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryMachinePartService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;
import com.zworks.pdsys.services.WareHouseMachinePartService;
import com.zworks.pdsys.services.WareHousePnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	@Autowired
	WareHousePnService wareHousePnService;
	@Autowired
	WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseDeliveryMachinePartService wareHouseDeliveryMachinePartService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	@RequestMapping("/list/main")
    public String listMain(@RequestParam(name="type",required = false, defaultValue="pn")String type, 
    		WareHouseListFormBean formBean,
    		Model model) {

		if(formBean == null) {
			formBean = new WareHouseListFormBean();
		}
		
		if(type.equals("bom")) {
			WareHouseBOMModel whBom = formBean.getWareHouseBOM();
			List<?> list = wareHouseBOMService.queryList(whBom);
			model.addAttribute("list", list);
		}
		else if(type.equals("pn")) {
			WareHousePnModel whPn = formBean.getWareHousePn();
			List<?> list = wareHousePnService.queryList(whPn);
			model.addAttribute("list", list);
		}
		else if(type.equals("machinepart")) {
			WareHouseMachinePartModel whPn = formBean.getWareHouseMachinePart();
			List<?> list = wareHouseMachinePartService.queryList(whPn);
			model.addAttribute("list", list);
		}
		else {
			throw new PdsysException("错误参数:/list/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
				
		model.addAttribute("formBean", formBean);
		model.addAttribute("type", type);
		return "warehouse/list/main";
    }
	
	/**
	 * 添加到出库单
	 * */
	@RequestMapping(value="/add/delivery/{type}")
	@ResponseBody
    public JSONResponse addDelivery(
    		@PathVariable String type,
    		@RequestBody WareHouseAddDeliveryObjFormBean wareHouseAddDeliveryObjFormBean,
    		Model model) {
		
		if("bom".equals(type)) {
			wareHouseDeliveryBOMService.addOrUpdate(wareHouseAddDeliveryObjFormBean.getWareHouseBOMIds(),
					wareHouseAddDeliveryObjFormBean.getWareHouseDeliveryBOM());
			return JSONResponse.success();
		} else if("pn".equals(type)) {
			wareHouseDeliveryPnService.addOrUpdate(wareHouseAddDeliveryObjFormBean.getWareHousePnIds(),
					wareHouseAddDeliveryObjFormBean.getWareHouseDeliveryPn());
			return JSONResponse.success();
		} else if("machinepart".equals(type)) {
			wareHouseDeliveryMachinePartService.addOrUpdate(wareHouseAddDeliveryObjFormBean.getWareHouseMachinePartIds(),
					wareHouseAddDeliveryObjFormBean.getWareHouseDeliveryMachinePart());
			return JSONResponse.success();
		} else {
			throw new PdsysException(PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
    }
}
