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
	private int state;
	private int type;
	private String comment;
	private List<WareHouseDeliveryPnModel> wareHouseDeliveryPns;
	private List<WareHouseDeliveryBOMModel> wareHouseDeliveryBOMs;
	
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<WareHouseDeliveryBOMModel> getWareHouseDeliveryBOMs() {
		return wareHouseDeliveryBOMs;
	}

	public void setWareHouseDeliveryBOMs(List<WareHouseDeliveryBOMModel> wareHouseDeliveryBOMs) {
		this.wareHouseDeliveryBOMs = wareHouseDeliveryBOMs;
	}
}
