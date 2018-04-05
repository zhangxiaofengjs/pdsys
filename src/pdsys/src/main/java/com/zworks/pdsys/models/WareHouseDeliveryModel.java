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
	private Date deliveryTime;
	
	public WareHouseDeliveryModel() {
		setUser(UserModel.Empty);
	}
	
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
}
