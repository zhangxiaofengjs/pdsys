package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.services.MachinePartService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/15
 */
@Controller
@RequestMapping("/machinepart")
public class MachinePartController {
	@Autowired
	MachinePartService machinePartService;
	
	@RequestMapping(value= {"/list/json"})
	@ResponseBody
    public List<MachinePartModel> list(MachinePartModel machinePart, Model model) {
		List<MachinePartModel> list = machinePartService.queryList(machinePart);
		
		return list;
    }
	
	@RequestMapping(value= {"/add"})
	@ResponseBody
	public JSONResponse add(@RequestBody MachinePartModel machinePart, Model model) {
		if(machinePartService.exists(machinePart)) {
			return JSONResponse.error("该品番零件已经存在");
		}
		machinePartService.add(machinePart);
		return JSONResponse.success();
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
    public JSONResponse update(@RequestBody MachinePartModel machinePart) {
		machinePartService.update(machinePart);
		return JSONResponse.success();
    }
}
