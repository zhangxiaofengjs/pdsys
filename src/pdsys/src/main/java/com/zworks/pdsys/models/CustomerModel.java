package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: ZHAI
 * @version: 2018/04/03
 */
@Alias("customerModel")
public class CustomerModel extends BaseModel {

	//顾客名
	private String name;
	
	//地址
	private String address;
	
	//联系方式
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
