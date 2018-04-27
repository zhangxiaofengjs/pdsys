package com.zworks.pdsys.business.beans;

public class UserChangePwdFormBean {
	private int id;
	private String password;
	private String password2;
	private String password3;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getPassword3() {
		return password3;
	}
	public void setPassword3(String password3) {
		this.password3 = password3;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
