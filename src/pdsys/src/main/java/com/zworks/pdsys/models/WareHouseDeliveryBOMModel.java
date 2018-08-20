package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryBOMModel")
public class WareHouseDeliveryBOMModel extends WareHouseBOMModel{
	public static final String FCK_FUZZYPNSEARCH = "fuzzyPnSearch";
	public static final String FCK_DELIVERYSTART = "deliveryStart";
	public static final String FCK_DELIVERYEND = "deliveryEnd";
	public static final String FCK_GROUPBYBOM = "groupByBOM";
	
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
