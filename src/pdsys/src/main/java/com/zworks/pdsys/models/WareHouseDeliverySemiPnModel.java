package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Alias("wareHouseDeliverySemiPnModel")
public class WareHouseDeliverySemiPnModel extends WareHouseSemiPnModel{
	private WareHouseDeliveryModel wareHouseDelivery;
	private WareHouseSemiPnModel wareHouseSemiPn;//实际在库
	
	public WareHouseDeliverySemiPnModel() {
	}
	
	public WareHouseDeliveryModel getWareHouseDelivery() {
		return wareHouseDelivery;
	}

	public void setWareHouseDelivery(WareHouseDeliveryModel wareHouseDelivery) {
		this.wareHouseDelivery = wareHouseDelivery;
	}

	public WareHouseSemiPnModel getWareHouseSemiPn() {
		return wareHouseSemiPn;
	}

	public void setWareHouseSemiPn(WareHouseSemiPnModel wareHouseSemiPn) {
		this.wareHouseSemiPn = wareHouseSemiPn;
	}
}
