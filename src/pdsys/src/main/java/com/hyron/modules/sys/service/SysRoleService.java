package com.hyron.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.hyron.modules.sys.entity.SysRoleEntity;

/**
 * 角色
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {

	SysRoleEntity queryObject(Long roleId);

	List<SysRoleEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
