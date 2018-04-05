package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryPnModel")
public class WareHouseDeliveryPnModel extends BaseModel{
	public static final WareHouseDeliveryPnModel Empty = new WareHouseDeliveryPnModel();
	
	private WareHouseDeliveryModel wareHouseDelivery;
	private WareHousePnModel wareHousePn;
	private float num;
	
	public WareHouseDeliveryPnModel() {
		setWareHouseDelivery(WareHouseDeliveryModel.Empty);
		setWareHousePn(WareHousePnModel.Empty);
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public WareHouseDeliveryModel getWareHouseDelivery() {
		return wareHouseDelivery;
	}

	public void setWareHouseDelivery(WareHouseDeliveryModel wareHouseDelivery) {
		this.wareHouseDelivery = wareHouseDelivery;
	}

	public WareHousePnModel getWareHousePn() {
		return wareHousePn;
	}

	public void setWareHousePn(WareHousePnModel wareHousePn) {
		this.wareHousePn = wareHousePn;
	}
}
