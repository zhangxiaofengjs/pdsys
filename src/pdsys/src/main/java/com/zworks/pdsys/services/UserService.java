package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.enumClass.ROLE;
import com.zworks.pdsys.common.security.PdSysPasswordEncoder;
import com.zworks.pdsys.mappers.UserMapper;
import com.zworks.pdsys.mappers.UserRoleMapper;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.UserRoleModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/08
 */
@Service
public class UserService {
	@Autowired
    private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	PdSysPasswordEncoder encoder;
	
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
	
	public UserModel queryOneWithPassword(UserModel user) {
		user.getFilterCond().put("pwd", true);
		return queryOne(user);
	}
	
	public UserModel queryOne(UserModel user) {
		List<UserModel> us = queryList(user);
		
		if(us.size() ==1) {
			return us.get(0);
		}
		return null;
	}
	public void changePassword(UserModel user) {
		UserModel u=new UserModel();
		u.setId(user.getId());
		u.setPassword(encoder.encode(user.getPassword()));
		userMapper.changePassword(u);
	}
	public boolean isPasswordMatch(String p1, String p2) {
		return encoder.matches(p1, p2);
	}
	
	@Transactional
	public void updateAuth(UserModel u, HashMap<String, String> r) {
		for(UserRoleModel role : u.getRoles()) {
			userRoleMapper.delete(role);
		}

		for(String role : ROLE.ROLES) {
			if("on".equals(r.get(role))) {
				UserRoleModel rr = new UserRoleModel();
				rr.setRole(role);
				rr.setUser(u);
				userRoleMapper.add(rr);
			}
		}

		update(u);
	}
}
