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
	private UserModel user;
	private Date entryTime;
	private String no;
	private int state = -1;
	private int type = -1;
	private String comment;
	private List<WareHouseEntryPnModel> wareHouseEntryPns;
	private List<WareHouseEntryBOMModel> wareHouseEntryBOMs;
	private List<WareHouseEntryMachinePartModel> wareHouseEntryMachineParts;

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

	public List<WareHouseEntryBOMModel> getWareHouseEntryBOMs() {
		return wareHouseEntryBOMs;
	}

	public void setWareHouseEntryBOMs(List<WareHouseEntryBOMModel> wareHouseEntryBOMs) {
		this.wareHouseEntryBOMs = wareHouseEntryBOMs;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<WareHouseEntryMachinePartModel> getWareHouseEntryMachineParts() {
		return wareHouseEntryMachineParts;
	}

	public void setWareHouseEntryMachineParts(List<WareHouseEntryMachinePartModel> wareHouseEntryMachineParts) {
		this.wareHouseEntryMachineParts = wareHouseEntryMachineParts;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
}
