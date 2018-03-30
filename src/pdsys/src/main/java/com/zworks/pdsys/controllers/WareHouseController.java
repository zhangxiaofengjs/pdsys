package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.services.WareHouseBOMService;

@Controller
@RequestMapping("/warehouse")
public class WareHouseController {
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	
	@RequestMapping("/list/main")
    public String main(@RequestParam(name="type",required = false, defaultValue="bom")String type, 
    		WareHouseBOMModel filterObj,
    		Model model) {
		model.addAttribute("type", type);

		if(filterObj==null) {
			filterObj = WareHouseBOMModel.Empty;
		}
		model.addAttribute("filterObj", filterObj);
		
		if(type.equals("bom")) {
			List<WareHouseBOMModel> list = wareHouseBOMService.queryList(filterObj);
			model.addAttribute("wareHouseBoms", list);
		}
		
		return "warehouse/list/main";
    }
}
