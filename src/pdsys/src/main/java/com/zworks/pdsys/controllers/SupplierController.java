package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.services.SupplierService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {
	@Autowired
	SupplierService supplierService;
	
	/**
	 * 供应商一览
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public JSONResponse listJson(SupplierModel supplier, Model model) {
		return JSONResponse.success().put("suppliers", supplierService.queryList(supplier));
	}
	
	/**
	 * 供应商详细
	 * @param JSONResponse 
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public JSONResponse delete(@RequestBody SupplierModel supplier, Model model) {
		
		SupplierModel sup = supplierService.queryObject(supplier.getId());
		return JSONResponse.success().put("sup", sup);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONResponse add(@RequestBody SupplierModel supplier, Model model) {
		SupplierModel s = supplierService.queryOne(supplier);
		if(s != null) {
			return JSONResponse.error("已经存在供应商");
		}
		supplierService.add(supplier);
		return JSONResponse.success().put("supplier", supplier);
	}
}
