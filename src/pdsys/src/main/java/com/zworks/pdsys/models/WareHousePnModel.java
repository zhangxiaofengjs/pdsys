package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("wareHousePnModel")
public class WareHousePnModel extends BaseModel{
	public static final WareHousePnModel Empty = new WareHousePnModel();
	
	private OrderPnModel orderPn;
	private float num;
	private int type;
	
	public WareHousePnModel() {
		orderPn = OrderPnModel.Empty;
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
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
}
