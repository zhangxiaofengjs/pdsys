package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/06/02
 */
@Alias("approvalNodeModel")
public class ApprovalNodeModel extends BaseModel {
	private ApprovalNodeModel nextNode;
	public ApprovalNodeModel getNextNode() {
		return nextNode;
	}
	public void setNextNode(ApprovalNodeModel nextNode) {
		this.nextNode = nextNode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserModel> getUsers() {
		return users;
	}
	public void setUsers(List<UserModel> users) {
		this.users = users;
	}
	private String name;
	private List<UserModel> users;

}
