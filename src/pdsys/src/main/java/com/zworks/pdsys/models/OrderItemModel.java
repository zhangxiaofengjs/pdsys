package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/31
 */
@Alias("orderItemModel")
public class OrderItemModel extends BaseModel{
	public static final OrderItemModel Empty = new OrderItemModel();

	private PnModel pn;
	private OrderModel order;
	private float num;
	private float rejectRatio;
	
	public OrderItemModel() {
		pn = PnModel.Empty;
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
	public float getRejectRatio() {
		return rejectRatio;
	}
	public void setRejectRatio(float rejectRatio) {
		this.rejectRatio = rejectRatio;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}
}
