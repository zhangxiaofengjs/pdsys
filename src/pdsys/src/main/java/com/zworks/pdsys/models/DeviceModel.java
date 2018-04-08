package com.zworks.pdsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Alias("deviceModel")
public class DeviceModel extends BaseModel {
	private String no;
	private MachineModel machine;
	private PlaceModel place;
	private UserModel user;
	private Date maitenacedDate;
	private int state;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public MachineModel getMachine() {
		return machine;
	}
	public void setMachine(MachineModel machine) {
		this.machine = machine;
	}
	public PlaceModel getPlace() {
		return place;
	}
	public void setPlace(PlaceModel place) {
		this.place = place;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public Date getMaitenacedDate() {
		return maitenacedDate;
	}
	public void setMaitenacedDate(Date maitenacedDate) {
		this.maitenacedDate = maitenacedDate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
