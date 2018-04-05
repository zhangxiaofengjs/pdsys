package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.models.CustomerModel;
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
}
