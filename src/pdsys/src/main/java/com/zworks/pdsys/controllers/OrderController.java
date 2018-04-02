package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/list")
    public String list(Model model) {
		
		List<OrderModel> list = orderService.queryList();
		model.addAttribute("orders", list);
		
        return "order/list";
    }
}
