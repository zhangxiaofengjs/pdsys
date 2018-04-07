package com.zworks.pdsys.models;

import java.util.Date;
import java.util.List;

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
	private int state;
	private List<WareHouseEntryPnModel> wareHouseEntryPns;
	
	public WareHouseEntryModel() {
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

	public List<WareHouseEntryPnModel> getWareHouseEntryPns() {
		return wareHouseEntryPns;
	}

	public void setWareHouseEntryPns(List<WareHouseEntryPnModel> wareHouseEntryPns) {
		this.wareHouseEntryPns = wareHouseEntryPns;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
