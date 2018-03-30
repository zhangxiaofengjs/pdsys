package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHousePnService;

@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	WareHousePnService wareHousePnService;
	
	@RequestMapping("/list/main")
    public String main(@RequestParam(name="type",required = false, defaultValue="bom")String type, 
    		WareHouseBOMModel filterBomObj,
    		WareHousePnModel filterPnObj,
    		Model model) {

		if(type.equals("bom")) {
			WareHouseBOMModel filterObj = filterBomObj==null?WareHouseBOMModel.Empty:filterBomObj;
			List<?> list = wareHouseBOMService.queryList(filterBomObj);
			model.addAttribute("list", list);
			model.addAttribute("filterBomObj", filterObj);
		}
		if(type.equals("pn")) {
			WareHousePnModel filterObj = filterBomObj==null?WareHousePnModel.Empty:filterPnObj;
			List<?> list = wareHousePnService.queryList(filterObj);
			model.addAttribute("list", list);
			model.addAttribute("filterPnObj", filterObj);
		}
		
		model.addAttribute("type", type);
		return "warehouse/list/main";
    }
}
