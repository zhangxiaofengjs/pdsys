package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.services.MachineService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Controller
@RequestMapping("/machine")
public class MachineController {
	@Autowired
	MachineService machineService;
	
	@RequestMapping(value= {"/list/json"})
	@ResponseBody
    public List<MachineModel> list(MachineModel machine, Model model) {
		List<MachineModel> list = machineService.queryList(machine);
		
		return list;
    }
	
	@RequestMapping(value="/get")
	@ResponseBody
    public JSONResponse get(@RequestBody MachineModel machine) {
		MachineModel m = machineService.queryOne(machine);
		return JSONResponse.success().put("machine", m);
    }
	
	@RequestMapping(value= {"/add"})
	@ResponseBody
	public JSONResponse add(@RequestBody MachineModel machine, Model model) {
		if(machineService.exists(machine)) {
			return JSONResponse.error("该品番机器已经存在");
		}
		machineService.add(machine);
		return JSONResponse.success();
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
    public JSONResponse update(@RequestBody MachineModel machine) {
		machineService.update(machine);
		return JSONResponse.success();
    }
	
	@RequestMapping(value="/editMachinePart")
	@ResponseBody
    public JSONResponse editMachinePart(@RequestBody MachineModel machine) {
		if(machineService.existsMachinePart(machine)) {
			machineService.updateMachinePart(machine);
		} else {
			machineService.addMachinePart(machine);
		}
		return JSONResponse.success();
    }
}
