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
import com.zworks.pdsys.models.ApprovalNodeModel;
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
	@Autowired
	ApprovalInfoService approvalInfoService;
	@Autowired
	ApprovalService approvalNodeService;
	
	public List<UserModel> queryList(UserModel filterObj) {
		return userMapper.queryList(filterObj);
	}
	public void add(UserModel filterObj) {
		filterObj.setPassword(encoder.encode("123"));
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
		
		//TODO 目前批准者没有设定的地方，在这里设定,以后多节点或者有设定批准流程的地方，应该改动到那边
		List<ApprovalNodeModel> list = approvalNodeService.queryList(null);
		ApprovalNodeModel node = null;
		if(list == null || list.size() == 0) {
			node = new ApprovalNodeModel();
			node.setName("采购单批复");
			approvalNodeService.add(node);
		} else {
			node = list.get(0);
		}
		
		List<UserModel> users = new ArrayList<UserModel>();
		users.add(u);
		node.setApprovalUsers(users);
		approvalNodeService.deleteApprovalUser(node);
		if("on".equals(r.get(ROLE.APPROVAL_PURCHASE))) {
			approvalNodeService.addApprovalUser(node);
		}
	}
}
