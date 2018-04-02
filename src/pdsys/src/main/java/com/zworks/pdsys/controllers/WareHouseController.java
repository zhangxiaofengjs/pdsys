package com.zworks.pdsys.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.form.beans.WareHouseListFormBean;
import com.zworks.pdsys.models.BaseModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHousePnService;

@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	@Autowired
	WareHousePnService wareHousePnService;
	
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
	
	@RequestMapping(value="/addcheckout")
	@ResponseBody
    public JSONResponse addcheckout( 
    		HttpServletRequest req,
    		@RequestBody WareHouseDeliveryBOMModel wareHouseDeliveryBOM,
    		Model model) {
		return JSONResponse.success("追加到出库订单成功!");
    }
}
