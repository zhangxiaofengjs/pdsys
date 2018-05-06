package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.services.BOMService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Controller
@RequestMapping("/bom")
public class BOMController {
	@Autowired
	BOMService bomService;
	
	@RequestMapping("/list/json")
	@ResponseBody
	public JSONResponse listJson(@RequestBody BOMModel bom, Model model) {
		List<BOMModel> list = bomService.queryList(bom);
		return JSONResponse.success().put("boms", list);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONResponse add(@RequestBody BOMModel bom, Model model) {
		List<BOMModel> list = bomService.queryList(bom);
		if(list.size() != 0) {
			return JSONResponse.error("该品番已经存在");
		}
		bomService.add(bom);
		return JSONResponse.success();
	}

	@RequestMapping("/get")
	@ResponseBody
	public JSONResponse get(@RequestBody BOMModel bom, Model model) {
		BOMModel b = bomService.queryById(bom.getId());
		if(b == null) {
			return JSONResponse.error("该品番不存在");
		}
		return JSONResponse.success().put("bom", b);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public JSONResponse update(@RequestBody BOMModel bom, Model model) {
		BOMModel u = bomService.queryById(bom.getId());
		if(u == null) {
			return JSONResponse.error("该品番不存在");
		}
		bomService.update(bom);
		return JSONResponse.success();
	}
	
	@RequestMapping("/addsupplier")
	@ResponseBody
	public JSONResponse addSupplier(@RequestBody BOMModel bom, Model model) {
		BOMModel b = bomService.queryById(bom.getId());
		if(b == null) {
			return JSONResponse.error("该品番不存在");
		}
		if(bomService.existsSupplier(b, bom.getSuppliers())) {
			return JSONResponse.error("该供应商已经追加");
		}
		
		bomService.addSupplier(bom);
		return JSONResponse.success();
	}
	
	@RequestMapping("/deletesupplier")
	@ResponseBody
	public JSONResponse deleteSupplier(@RequestBody BOMModel bom, Model model) {
		BOMModel b = bomService.queryById(bom.getId());
		if(b == null) {
			return JSONResponse.error("该品番不存在");
		}
		if(bomService.existsSupplier(b, bom.getSuppliers())) {
			bomService.deleteSupplier(bom);
		}
		return JSONResponse.success();
	}
}
