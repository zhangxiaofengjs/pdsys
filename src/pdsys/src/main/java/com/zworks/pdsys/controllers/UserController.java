package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.OrderService;
import com.zworks.pdsys.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/Json/list")
	@ResponseBody
    public String list(UserModel user, Model model) {
		List<UserModel> list = userService.queryList(user);
		model.addAttribute("users", list);

        return "order/list";
    }
}
