package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.UserMapper;
import com.zworks.pdsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/08
 */
@Service
public class UserService {
	@Autowired
    private UserMapper userMapper;
	
	public List<UserModel> queryList(UserModel filterObj) {
		return userMapper.queryList(filterObj);
	}
	public void add(UserModel filterObj) {
		userMapper.add(filterObj);
	}
	public void update(UserModel filterObj) {
		userMapper.update(filterObj);
	}
	public UserModel queryById(int id) {
		UserModel u = new UserModel();
		u.setId(id);
		List<UserModel> us = queryList(u);
		
		if(us.size() ==1) {
			return us.get(0);
		}
		return null;
	}
}
