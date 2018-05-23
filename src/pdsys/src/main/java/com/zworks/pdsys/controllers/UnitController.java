package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.UnitModel;
import com.zworks.pdsys.services.UnitService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Controller
@RequestMapping("/unit")
public class UnitController {
	@Autowired
	UnitService unitService;
	
	@RequestMapping("/list/json")
	@ResponseBody
	public JSONResponse listJson(UnitModel unit, Model model) {
		return JSONResponse.success().put("units", unitService.queryList(unit));
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONResponse add(@RequestBody UnitModel unit, Model model) {
		UnitModel u = unitService.queryOne(unit);
		if(u != null) {
			return JSONResponse.error("已经存在单位");
		}
		unitService.add(unit);
		return JSONResponse.success().put("unit", unit);
	}
}
