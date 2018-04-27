package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Mapper
public interface UserMapper {
	
	List<UserModel> queryList(UserModel filterObj);

	void add(UserModel filterObj);

	void update(UserModel filterObj);

	void changePassword(UserModel user);
}
