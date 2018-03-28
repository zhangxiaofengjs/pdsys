package com.hyron.modules.sys.service;

import java.util.Set;

import com.hyron.modules.sys.entity.SysUserEntity;
import com.hyron.modules.sys.entity.SysUserTokenEntity;

/**
 * shiro相关接口
 * @author ZHAI
 * 
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
	
	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserPermissions(long userId);

	SysUserTokenEntity queryByToken(String token);

	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 */
	SysUserEntity queryUser(Long userId);
}
