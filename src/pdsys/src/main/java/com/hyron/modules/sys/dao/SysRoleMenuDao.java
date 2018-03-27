package com.hyron.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2016年9月18日 上午9:33:46
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
