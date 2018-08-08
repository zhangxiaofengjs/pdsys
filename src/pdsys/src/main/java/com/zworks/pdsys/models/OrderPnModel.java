package com.zworks.pdsys.models;

import javax.validation.constraints.Min;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/31
 */
@Alias("orderPnModel")
public class OrderPnModel extends BaseModel{
	private PnModel pn;
	private OrderModel order;
	
	@Min(value = 1,message="数量必须是一个数字，其值必须大于0！")
	private float num;
	private float deliveredNum;
	private float price;
	private UnitModel priceUnit;
	private WareHousePnModel whpn;
	
	public OrderPnModel() {
	}
	
	public PnModel getPn() {
		return pn;
	}
	public void setPn(PnModel pn) {
		this.pn = pn;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public WareHousePnModel getWhpn() {
		return whpn;
	}

	public void setWhpn(WareHousePnModel whpn) {
		this.whpn = whpn;
	}

	public float getDeliveredNum() {
		return deliveredNum;
	}

	public void setDeliveredNum(float deliveredNum) {
		this.deliveredNum = deliveredNum;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public UnitModel getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(UnitModel priceUnit) {
		this.priceUnit = priceUnit;
	}

}
