package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryPnModel")
public class WareHouseDeliveryPnModel extends WareHousePnModel{
	private WareHouseDeliveryModel wareHouseDelivery;
	private WareHousePnModel wareHousePn;//实际在库
	private OrderPnModel orderPn;
	
	public WareHouseDeliveryPnModel() {
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

	public WareHousePnModel getWareHousePn() {
		return wareHousePn;
	}

	public void setWareHousePn(WareHousePnModel wareHousePn) {
		this.wareHousePn = wareHousePn;
	}
}
