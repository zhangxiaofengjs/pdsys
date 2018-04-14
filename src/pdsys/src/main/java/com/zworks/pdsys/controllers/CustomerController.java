package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.CustomerModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/list")
	@ResponseBody
    public List<CustomerModel> list(CustomerModel customer, Model model) {
		List<CustomerModel> list = customerService.queryList(customer);
        return list;
    }
	
	@RequestMapping("/get")
	@ResponseBody
	public JSONResponse get(@RequestBody CustomerModel customer, Model model) {
		CustomerModel c = customerService.queryById(customer.getId());
		if(c == null) {
			return JSONResponse.error("客户不存在。");
		}
		return JSONResponse.success().put("customer", c);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONResponse add(@RequestBody CustomerModel customer, Model model) {
		List<CustomerModel> list = customerService.queryList(customer);
		if(list.size() != 0) {
			return JSONResponse.error("该客户名已经存在。");
		}
		customerService.add(customer);
		return JSONResponse.success();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public JSONResponse update(@RequestBody CustomerModel customer, Model model) {
		CustomerModel u = customerService.queryById(customer.getId());
		if(u == null) {
			return JSONResponse.error("该客户名已经不存在。");
		}
		customerService.update(customer);
		return JSONResponse.success();
	}
}
