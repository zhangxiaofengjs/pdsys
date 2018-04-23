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
	private float semiProducedNum;
	private float producedNum;
	private float defectiveNum;
	
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

	public float getSemiProducedNum() {
		return semiProducedNum;
	}

	public void setSemiProducedNum(float semiProducedNum) {
		this.semiProducedNum = semiProducedNum;
	}

	public float getProducedNum() {
		return producedNum;
	}

	public void setProducedNum(float producedNum) {
		this.producedNum = producedNum;
	}

	public float getDefectiveNum() {
		return defectiveNum;
	}

	public void setDefectiveNum(float defectiveNum) {
		this.defectiveNum = defectiveNum;
	}
}
