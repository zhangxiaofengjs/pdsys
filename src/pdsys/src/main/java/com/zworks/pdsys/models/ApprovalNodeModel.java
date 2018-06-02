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
	private String name;
	private List<UserModel> approvalUsers;
	
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
	public List<UserModel> getApprovalUsers() {
		return approvalUsers;
	}
	public void setApprovalUsers(List<UserModel> approvalUsers) {
		this.approvalUsers = approvalUsers;
	}
}
