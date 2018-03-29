//package com.hyron.modules.api.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hyron.common.utils.R;
//import com.hyron.modules.api.annotation.Login;
//import com.hyron.modules.api.annotation.LoginUser;
//import com.hyron.modules.api.entity.UserEntity;
//import com.hyron.modules.api.service.UserService;
//
///**
// * 用户接口
// * 
// * @author ZHAI
// * 
// * @date 2017-03-23 15:47
// */
//@RestController
//@RequestMapping("/api")
//@Api("用户接口")
//public class ApiUserController {
//	
//	@Autowired
//	private UserService userService;
//
//	@Login
//	@GetMapping("userInfo")
//	@ApiOperation("获取用户信息")
//	public R userInfo(@LoginUser UserEntity user) {
//		return R.ok().put("user", user);
//	}
//
//	@Login
//	@GetMapping("mobile/{mobile}")
//	@ApiOperation("获取用户信息")
//	public R getUserInfoByMobile(@PathVariable("mobile") String mobile) {
//		UserEntity user = userService.queryByMobile(mobile);
//		Map<String, Object> map = new HashMap<>();
//		map.put("user", user);
//		map.put("mobile", mobile);
//		return R.ok(map);
//	}
//
//	@GetMapping("notToken")
//	@ApiOperation("无Token验证")
//	public R notToken() {
//		return R.ok().put("msg", "无Token验证");
//	}
//}
