package com.hyron.modules.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyron.common.utils.R;
import com.hyron.common.validator.Assert;
import com.hyron.modules.api.service.UserService;

/**
 * 注册
 * @author ZHAI
 * 
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api")
@Api("注册接口")
public class ApiRegisterController {
	
	@Autowired
	private UserService userService;

	@PostMapping("register")
	@ApiOperation("注册")
	public R register(String mobile, String username, String password) {
		Assert.isBlank(mobile, "手机号不能为空");
		Assert.isBlank(password, "密码不能为空");
		userService.save(mobile, username, password);
		return R.ok();
	}
}
