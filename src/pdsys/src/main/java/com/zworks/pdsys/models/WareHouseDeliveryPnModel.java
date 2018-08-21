package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryPnModel")
public class WareHouseDeliveryPnModel extends WareHousePnModel{
	public static final String FCK_FUZZYPNSEARCH = "fuzzyPnSearch";
	public static final String FCK_DELIVERYSTART = "deliveryStart";
	public static final String FCK_DELIVERYEND = "deliveryEnd";
	public static final String FCK_GROUPBYPN = "groupByPn";
	
	private WareHouseDeliveryModel wareHouseDelivery;
	private WareHousePnModel wareHousePn;//实际在库
	private OrderModel order;
	
	public WareHouseDeliveryPnModel() {
	}
	
	public WareHouseDeliveryModel getWareHouseDelivery() {
		return wareHouseDelivery;
	}

	public void setWareHouseDelivery(WareHouseDeliveryModel wareHouseDelivery) {
		this.wareHouseDelivery = wareHouseDelivery;
	}

	public WareHousePnModel getWareHousePn() {
		return wareHousePn;
	}

	public void setWareHousePn(WareHousePnModel wareHousePn) {
		this.wareHousePn = wareHousePn;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}
}
