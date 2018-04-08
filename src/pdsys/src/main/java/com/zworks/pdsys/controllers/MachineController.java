package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
