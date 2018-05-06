package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryBOMModel")
public class WareHouseDeliveryBOMModel extends WareHouseBOMModel{
	private WareHouseDeliveryModel wareHouseDelivery;
	private WareHouseBOMModel wareHouseBOM;
	
	public WareHouseDeliveryBOMModel() {
	}
	
	public WareHouseDeliveryModel getWareHouseDelivery() {
		return wareHouseDelivery;
	}

	public void setWareHouseDelivery(WareHouseDeliveryModel wareHouseDelivery) {
		this.wareHouseDelivery = wareHouseDelivery;
	}

	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}

	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
	}
}
