package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryPnModel")
public class WareHouseDeliveryPnModel extends BaseModel{
	
	private WareHouseDeliveryModel wareHouseDelivery;
	private OrderPnModel orderPn;
	private WareHousePnModel wareHousePn;
	private float num;
	private int type;
	
	public WareHouseDeliveryPnModel() {
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public WareHouseDeliveryModel getWareHouseDelivery() {
		return wareHouseDelivery;
	}

	public void setWareHouseDelivery(WareHouseDeliveryModel wareHouseDelivery) {
		this.wareHouseDelivery = wareHouseDelivery;
	}

	public OrderPnModel getOrderPn() {
		return orderPn;
	}

	public void setOrderPn(OrderPnModel orderPn) {
		this.orderPn = orderPn;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public WareHousePnModel getWareHousePn() {
		return wareHousePn;
	}

	public void setWareHousePn(WareHousePnModel wareHousePn) {
		this.wareHousePn = wareHousePn;
	}
}
