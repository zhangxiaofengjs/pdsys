package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zworks.pdsys.services.CustomerService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/list")
    public String list(Model model) {
        return "/notice/list";
    }
}
