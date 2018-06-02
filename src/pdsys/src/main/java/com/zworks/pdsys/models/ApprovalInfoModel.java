package com.zworks.pdsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/06/02
 */
@Alias("approvalInfoModel")
public class ApprovalInfoModel {
	private int id;
	private ApprovalNodeModel node;
	private UserModel approvalUser;
	private Date updateTime;
	private int state;
	
	public ApprovalNodeModel getNode() {
		return node;
	}
	public void setNode(ApprovalNodeModel node) {
		this.node = node;
	}
	public UserModel getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(UserModel approvalUser) {
		this.approvalUser = approvalUser;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
