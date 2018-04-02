package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("wareHousePnModel")
public class WareHousePnModel extends BaseModel{
	public static final WareHousePnModel Empty = new WareHousePnModel();
	
	private OrderItemModel orderItem;
	private float num;
	private int type;
	
	public WareHousePnModel() {
		orderItem = OrderItemModel.Empty;
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public OrderItemModel getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItemModel orderItem) {
		this.orderItem = orderItem;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
