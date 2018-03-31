package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.UserModel;

@Mapper
public interface UserMapper {
	
	List<UserModel> queryList(UserModel filterObj);
}
