package com.zworks.pdsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Alias("wareHouseEntryModel")
public class WareHouseEntryModel extends BaseModel{
	public static final WareHouseEntryModel Empty = new WareHouseEntryModel();
	
	private UserModel user;
	private Date entryTime;
	
	public WareHouseEntryModel() {
		setUser(UserModel.Empty);
	}
	
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
}
