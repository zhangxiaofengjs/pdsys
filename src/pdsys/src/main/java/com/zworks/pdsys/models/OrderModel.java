package com.zworks.pdsys.models;

import java.util.Date;

/**
 * 订单
 * 
 * @author ZHAI
 * @date 2018-03-30 13:22:06
 */
public class OrderModel extends BaseModel {
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	//订单编号
	private String no;
	
	//下单时间
	private Date orderDate;
	
	//交货期限
	private Date shipDeadDate;
	
	//交货时间
	private Date shipDate;
	
	//状态
	private int state;
	
	//状态
	private String comment;

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShipDeadDate() {
		return shipDeadDate;
	}

	public void setShipDeadDate(Date shipDeadDate) {
		this.shipDeadDate = shipDeadDate;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
