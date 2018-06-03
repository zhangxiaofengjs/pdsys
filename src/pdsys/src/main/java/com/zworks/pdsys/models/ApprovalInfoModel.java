package com.zworks.pdsys.models;

import java.util.Date;
import java.util.HashMap;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/06/02
 */
@Alias("approvalInfoModel")
public class ApprovalInfoModel {
	private int id;
	private int type;
	private ApprovalNodeModel node;
	private UserModel approvalUser;
	private UserModel requestUser;
	private Date updateTime;
	private int state;
	private HashMap<String, Object> filterCond = new HashMap<String, Object>();
	
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
	public HashMap<String, Object> getFilterCond() {
		return filterCond;
	}
	public void setFilterCond(HashMap<String, Object> filterCond) {
		this.filterCond = filterCond;
	}
	public UserModel getRequestUser() {
		return requestUser;
	}
	public void setRequestUser(UserModel requestUser) {
		this.requestUser = requestUser;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
