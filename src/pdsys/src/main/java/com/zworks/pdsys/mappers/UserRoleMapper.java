package com.zworks.pdsys.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.UserRoleModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/25
 */
@Mapper
public interface UserRoleMapper {

	void delete(UserRoleModel role);

	void add(UserRoleModel rr);
	
}
