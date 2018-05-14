package com.zworks.pdsys.models;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zworks.pdsys.common.utils.DateJsonDeserializer;
import com.zworks.pdsys.common.utils.DateJsonSerializer;

/**
 * 采购单
 * 
 * @author ZHAI
 * @date 2018-05-04 13:22:06
 */
@Alias("purchaseModel")
public class PurchaseModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	//编号
	private String no;
	
	//生成时间
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date createDate;
	
    //下单时间
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date purchaseDate;
	
	//到货时间
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date arriveDate;
	
	//状态
	private int state = -1;
	
	private WareHouseEntryModel wareHouseEntry;
	
	private UserModel user;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public WareHouseEntryModel getWareHouseEntry() {
		return wareHouseEntry;
	}

	public void setWareHouseEntry(WareHouseEntryModel wareHouseEntry) {
		this.wareHouseEntry = wareHouseEntry;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
