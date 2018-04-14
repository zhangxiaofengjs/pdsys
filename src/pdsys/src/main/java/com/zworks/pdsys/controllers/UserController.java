package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/list/json")
	@ResponseBody
    public List<UserModel> listJson(UserModel user, Model model) {
		List<UserModel> list = userService.queryList(user);
        return list;
    }
	
	@RequestMapping("/get")
	@ResponseBody
	public JSONResponse get(@RequestBody UserModel user, Model model) {
		UserModel u = userService.queryById(user.getId());
		if(u == null) {
			return JSONResponse.error("用户不存在。");
		}
		return JSONResponse.success().put("user", u);
	}
	
	@RequestMapping("/list")
	public String list(UserModel user, Model model) {
		List<UserModel> list = userService.queryList(user);
		model.addAttribute("list", list);
		return "/sys/user/list";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONResponse add(@RequestBody UserModel user, Model model) {
		List<UserModel> list = userService.queryList(user);
		if(list.size() != 0) {
			return JSONResponse.error("该工号姓名用户已经存在。");
		}
		userService.add(user);
		return JSONResponse.success();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public JSONResponse update(@RequestBody UserModel user, Model model) {
		UserModel u = userService.queryById(user.getId());
		if(u == null) {
			return JSONResponse.error("该工号姓名用户不存在。");
		}
		userService.update(user);
		return JSONResponse.success();
	}
}
