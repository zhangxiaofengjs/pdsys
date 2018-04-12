package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.services.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
	@Autowired
	SupplierService supplierService;
	
	/**
	 * 供应商详细
	 * @param JSONResponse 
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public JSONResponse delete(@RequestParam(name="id") int id, Model model) {
		
		SupplierModel supplier = supplierService.queryObject(id);
		model.addAttribute("supplier", supplier);
		return JSONResponse.success();
	}
}
