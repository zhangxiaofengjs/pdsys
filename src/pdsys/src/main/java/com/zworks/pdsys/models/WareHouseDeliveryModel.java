package com.zworks.pdsys.models;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/02
 */
@Alias("wareHouseDeliveryModel")
public class WareHouseDeliveryModel extends BaseModel{
	private UserModel user;
	private Date deliveryTime;
	private List<WareHouseDeliveryPnModel> wareHouseDeliveryPns;
	
	public WareHouseDeliveryModel() {
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

	public List<WareHouseDeliveryPnModel> getWareHouseDeliveryPns() {
		return wareHouseDeliveryPns;
	}

	public void setWareHouseDeliveryPns(List<WareHouseDeliveryPnModel> wareHouseDeliveryPns) {
		this.wareHouseDeliveryPns = wareHouseDeliveryPns;
	}
}
