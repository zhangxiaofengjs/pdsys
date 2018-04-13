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
	private float producedNum;
	private float semiProducedNum;
	
	public WareHousePnModel() {
		orderPn = OrderPnModel.Empty;
	}

	public OrderPnModel getOrderPn() {
		return orderPn;
	}

	public void setOrderPn(OrderPnModel orderPn) {
		this.orderPn = orderPn;
	}

	public float getProducedNum() {
		return producedNum;
	}

	public void setProducedNum(float producedNum) {
		this.producedNum = producedNum;
	}

	public float getSemiProducedNum() {
		return semiProducedNum;
	}

	public void setSemiProducedNum(float semiProducedNum) {
		this.semiProducedNum = semiProducedNum;
	}
}
