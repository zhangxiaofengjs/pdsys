package com.zworks.pdsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/02
 */
@Alias("wareHouseDeliveryModel")
public class WareHouseDeliveryModel extends BaseModel{
	public static final WareHouseDeliveryModel Empty = new WareHouseDeliveryModel();
	
	private UserModel user;
	private float num;
	private Date time;
	
	public WareHouseDeliveryModel() {
		setUser(UserModel.Empty);
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
}
