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
import com.hyron.modules.api.utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录授权
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/api")
@Api("登录接口")
public class ApiLoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * 登录
	 */
	@PostMapping("login")
	@ApiOperation("登录")
	public R login(String mobile, String password) {
		Assert.isBlank(mobile, "手机号不能为空");
		Assert.isBlank(password, "密码不能为空");
		//用户登录
		long userId = userService.login(mobile, password);
		//生成token
		String token = jwtUtils.generateToken(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", jwtUtils.getExpire());
		return R.ok(map);
	}
}
