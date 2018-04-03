package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.UserMapper;
import com.zworks.pdsys.models.UserModel;

@Service
public class UserService {
	@Autowired
    private UserMapper userMapper;
	
	public List<UserModel> queryList(UserModel filterObj) {
		return userMapper.queryList(filterObj);
	}
}
