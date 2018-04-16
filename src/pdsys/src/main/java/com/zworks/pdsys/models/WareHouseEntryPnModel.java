package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Alias("wareHouseEntryPnModel")
public class WareHouseEntryPnModel extends BaseModel{
	private OrderPnModel orderPn;
	private WareHousePnModel wareHousePn;
	private WareHouseEntryModel wareHouseEntry;
	private float producedNum;
	private float semiProducedNum;
	
	public WareHouseEntryPnModel() {
	}

	public WareHouseEntryModel getWareHouseEntry() {
		return wareHouseEntry;
	}

	public void setWareHouseEntry(WareHouseEntryModel wareHouseEntry) {
		this.wareHouseEntry = wareHouseEntry;
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

	public float getProducedNum() {
		return producedNum;
	}

	public void setProducedNum(float producedNum) {
		this.producedNum = producedNum;
	}

	public float getSemiProducedNum() {
		return semiProducedNum;
	}

	public void setSmiProducedNum(float semiProducedNum) {
		this.semiProducedNum = semiProducedNum;
	}
}
