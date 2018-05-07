package com.zworks.pdsys.models;

import java.util.Date;

import com.zworks.pdsys.common.security.PdSysLoginUser;

public class LogModel {
	private String description;
	private String method;
	private String args;
	private String ip;
	private String url;
	private PdSysLoginUser user;
	private long elapseTime;
	private Date time;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public PdSysLoginUser getUser() {
		return user;
	}
	public void setUser(PdSysLoginUser user) {
		this.user = user;
	}
	public long getElapseTime() {
		return elapseTime;
	}
	public void setElapseTime(long elapseTime) {
		this.elapseTime = elapseTime;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
