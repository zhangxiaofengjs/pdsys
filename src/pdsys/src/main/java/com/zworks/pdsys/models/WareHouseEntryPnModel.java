package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Alias("wareHouseEntryPnModel")
public class WareHouseEntryPnModel extends BaseModel{
	private OrderPnModel orderPn;
	private WareHouseEntryModel wareHouseEntry;
	private int type;
	private float num;
	
	public WareHouseEntryPnModel() {
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
