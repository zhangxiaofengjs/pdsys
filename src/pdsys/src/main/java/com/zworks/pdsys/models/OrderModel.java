package com.zworks.pdsys.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zworks.pdsys.common.utils.DateJsonDeserializer;
import com.zworks.pdsys.common.utils.DateJsonSerializer;

/**
 * 订单
 * 
 * @author ZHAI
 * @date 2018-03-30 13:22:06
 */
@Alias("orderModel")
public class OrderModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	//订单编号
	@NotEmpty(message="订单编号不能空")
	private String no;
	
	//下单时间
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date orderDate;
	
	//交货期限
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date shipDeadDate;
	
	//交货时间
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date shipDate;
	
	//状态
	private int state = -1;
	
	//备注
	private String comment;
	
	private UserModel user;
	
	private CustomerModel customer;
	
	private List<OrderPnModel> orderPns;
	
	private String attachment;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	public List<OrderPnModel> getOrderPns() {
		return orderPns;
	}

	public void setOrderPns(List<OrderPnModel> orderPns) {
		this.orderPns = orderPns;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
