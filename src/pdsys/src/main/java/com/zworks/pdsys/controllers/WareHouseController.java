package com.zworks.pdsys.controllers;

import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.form.beans.WareHouseAddDeliveryObjFormBean;
import com.zworks.pdsys.form.beans.WareHouseListFormBean;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;
import com.zworks.pdsys.services.WareHousePnService;

@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	@Autowired
	WareHousePnService wareHousePnService;
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	@RequestMapping("/list/main")
    public String main(@RequestParam(name="type",required = false, defaultValue="bom")String type, 
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
		
		if(type.equals("pn")) {
			WareHousePnModel whPn = formBean.getWareHousePn();
			List<?> list = wareHousePnService.queryList(whPn);
			model.addAttribute("list", list);
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
		} else {
			return JSONResponse.error("错误的请求:" + type);
		}
		
    }
}
