package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public JSONResponse delete(@RequestBody SupplierModel supplier, Model model) {
		
		SupplierModel sup = supplierService.queryObject(supplier.getId());
		return JSONResponse.success().put("sup", sup);
	}
}
