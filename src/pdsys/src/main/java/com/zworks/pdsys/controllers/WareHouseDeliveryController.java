package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Controller
@RequestMapping("/warehouse/delivery")
public class WareHouseDeliveryController {
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	@RequestMapping(value= {"/main", "/main/{type}", "/main/{type}/{id}"})
    public String main(
    		@PathVariable(name="type" ,required=false)String type,
    		@PathVariable(name="id", required=false)Integer id,
    		Model model) {

		if(type == null) {
			type = "pn";
		} else if(!(type.equals("bom") || type.equals("pn") || type.equals("machinepart"))) {
			throw new PdsysException("错误参数:/delivery/main/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		
		WareHouseDeliveryModel delivery;
		if(id != null) {
			delivery = wareHouseDeliveryService.queryOne(id);
			if(delivery == null) {
				throw new PdsysException("错误参数:/delivery/main/" + type + "/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM);
			}
		} else {
			delivery = new WareHouseDeliveryModel();
		}
		
		model.addAttribute("delivery", delivery);
		model.addAttribute("type", type);
		return "warehouse/delivery/main";
    }
	
	/**
	 * 新建出库单
	 * */
	@RequestMapping(value="/add/delivery")
	@ResponseBody
    public JSONResponse addDelivery(@RequestBody WareHouseDeliveryModel delivery, Model model) {
		wareHouseDeliveryService.add(delivery);
		return JSONResponse.success().put("id", delivery.getId());
    }
	
	/**
	 * 新建出库明细
	 * */
	@RequestMapping(value="/add/pn")
	@ResponseBody
    public JSONResponse addDeliveryPn(@RequestBody WareHouseDeliveryPnModel deliveryPn, Model model) {
		wareHouseDeliveryPnService.add(deliveryPn);
		return JSONResponse.success();
    }
	
	/**
	 * 删除出库单
	 * */
	@RequestMapping(value="/delete/delivery/{id}")
	@ResponseBody
	public JSONResponse deleteDelivery(@PathVariable(name="id", required=false)Integer id, Model model) {
		WareHouseDeliveryModel delivery = wareHouseDeliveryService.queryOne(id);
		if(delivery == null) {
			throw new PdsysException("错误参数:/delivery/delete/delivery/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM); 
		}
		wareHouseDeliveryService.delete(delivery);
		return JSONResponse.success();
	}
	
	/**
	 * 删除出库明细
	 * */
	@RequestMapping(value="/delete/pn")
	@ResponseBody
    public JSONResponse deleteDeliveryPn(@RequestBody List<WareHouseDeliveryPnModel> deliveryPns, Model model) {
		wareHouseDeliveryPnService.delete(deliveryPns);
		return JSONResponse.success();
    }
	
	/**
	 * 出库
	 * */
	@RequestMapping(value="/delivery/{id}")
	@ResponseBody
    public JSONResponse delivery(@PathVariable(name="id", required=false)Integer id, Model model) {
		WareHouseDeliveryModel delivery = wareHouseDeliveryService.queryOne(id);
		if(delivery == null) {
			throw new PdsysException("错误参数:/delivery/delivery/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM); 
		}
		if(wareHouseDeliveryService.delivery(delivery)) {
			return JSONResponse.success();
		} else {
			return JSONResponse.error("库存不足,请刷新页面。");
		}
    }
}
