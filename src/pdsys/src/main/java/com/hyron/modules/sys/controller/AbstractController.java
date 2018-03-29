//package com.hyron.modules.sys.controller;
//
//import org.apache.shiro.SecurityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.hyron.modules.sys.entity.SysUserEntity;
//
///**
// * Controller公共组件
// * 
// * @author ZHAI
// * 
// * @date 2016年11月9日 下午9:42:26
// */
//public abstract class AbstractController {
//
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//
//	protected SysUserEntity getUser() {
//		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
//	}
//
//	protected Long getUserId() {
//		return getUser().getUserId();
//	}
//}
