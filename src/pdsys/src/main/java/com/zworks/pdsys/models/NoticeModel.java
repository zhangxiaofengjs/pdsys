package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

@Alias("noticeModel")
public class NoticeModel extends BaseModel {
	private String content;
	private int type;
	private int refId;
	private int state = -1;
	private UserModel sender;
	private UserModel receiver;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public UserModel getSender() {
		return sender;
	}
	public void setSender(UserModel sender) {
		this.sender = sender;
	}
	public UserModel getReceiver() {
		return receiver;
	}
	public void setReceiver(UserModel receiver) {
		this.receiver = receiver;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
