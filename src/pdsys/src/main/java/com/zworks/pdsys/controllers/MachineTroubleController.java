package com.zworks.pdsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.MachineTroubleModel;
import com.zworks.pdsys.services.MachineTroubleService;

@Controller
@RequestMapping("/machineTrouble")
public class MachineTroubleController {
	@Autowired
	MachineTroubleService machineTroubleService;
	
	@RequestMapping(value= {"/list/json"})
	@ResponseBody
    public List<MachineTroubleModel> list(MachineTroubleModel machineTrouble, Model model) {
		List<MachineTroubleModel> list = machineTroubleService.queryList(machineTrouble);
		if( list == null )
			list = new ArrayList<MachineTroubleModel>();
		
		return list;
    }
	
	@RequestMapping(value="/get")
	@ResponseBody
    public JSONResponse get(@RequestBody MachineTroubleModel machineTrouble) {
		MachineTroubleModel m = machineTroubleService.queryOne(machineTrouble);
		return JSONResponse.success().put("machineTrouble", m);
    }
	
	@RequestMapping(value= {"/add"})
	@ResponseBody
	public JSONResponse add(@RequestBody MachineTroubleModel machineTrouble, Model model) {
		if(machineTroubleService.exists(machineTrouble)) {
			return JSONResponse.error("该故障已存在！");
		}
		machineTroubleService.add(machineTrouble);
		return JSONResponse.success().put("machineTrouble", machineTrouble);
	}
	
}
