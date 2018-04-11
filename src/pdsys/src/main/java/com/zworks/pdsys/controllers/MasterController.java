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
import com.zworks.pdsys.models.PlaceModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.services.PlaceService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/09
 */
@Controller
@RequestMapping("/sys/master")
public class MasterController {
	@Autowired
	PlaceService placeService;
	
	@RequestMapping(value= {"/main", "/main/{type}", "/main/{type}"})
    public String main(
    		@PathVariable(name="type" ,required=false)String type,
    		Model model) {

		if(type == null) {
			type = "customer";
		} else if(!(type.equals("customer") || type.equals("place") || 
				type.equals("bom") || type.equals("pn") || type.equals("machine"))) {
			throw new PdsysException("错误参数:/sys/master/main/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		
		model.addAttribute("type", type);
		model.addAttribute("list", placeService.queryList(new PlaceModel()));
		return "sys/master/main";
    }
}
