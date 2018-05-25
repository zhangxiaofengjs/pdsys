package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/18
 */
@Alias("userRoleModel")
public class UserRoleModel extends BaseModel {
	private String role;
	private UserModel user;
	
	public String getRole() {
		return role;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
