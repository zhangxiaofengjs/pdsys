package com.zworks.pdsys.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.zworks.pdsys.business.form.beans.UserChangePwdFormBean;
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
		//启用模糊查询
		user.getFilterCond().put("fuzzyNoSearch", true);
		
		List<UserModel> list = userService.queryList(user);
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		return "sys/user/list";
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
	
	@RequestMapping("/setauth")
	@ResponseBody
	public JSONResponse setAuth(@RequestBody HashMap<String, String> r, Model model) {
		int id = Integer.valueOf(r.get("id"));
		UserModel u = userService.queryById(id);
		if(u == null) {
			return JSONResponse.error("该用户不存在");
		}
		if("admin".equals(u.getNo())) {
			return JSONResponse.error("禁止编辑该账号");
		}

		userService.updateAuth(u, r);
		return JSONResponse.success();
	}
	
	@RequestMapping("/changepwd")
	@ResponseBody
	public JSONResponse changePwd(@RequestBody UserChangePwdFormBean formBean, Model model) {
		if(StringUtils.isNullOrEmpty(formBean.getPassword()) || 
			StringUtils.isNullOrEmpty(formBean.getPassword2()) ||
			StringUtils.isNullOrEmpty(formBean.getPassword3())) {
			return JSONResponse.error("密码不能为空");
		}
		
		if(!formBean.getPassword2().equals(formBean.getPassword3())) {
			return JSONResponse.error("新密码不一致");
		}
		
		UserModel u = new UserModel();
		u.setId(formBean.getId());
		u = userService.queryOneWithPassword(u);
		if(u == null) {
			return JSONResponse.error("该工号用户不存在。");
		}
		
		if(!userService.isPasswordMatch(formBean.getPassword(), u.getPassword())) {
			return JSONResponse.error("旧密码不对");
		}
		
		u.setPassword(formBean.getPassword2());
		userService.changePassword(u);
		return JSONResponse.success();
	}
	
	@RequestMapping("/initpwd")
	@ResponseBody
	public JSONResponse initPwd(@RequestBody UserModel user, Model model) {
		user.setPassword("123");
		userService.changePassword(user);
		return JSONResponse.success();
	}
}
