package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Alias("wareHouseDeliveryBOMModel")
public class WareHouseDeliveryBOMModel extends BaseModel{
	
	private WareHouseDeliveryModel wareHouseDelivery;
	private BOMModel bom;
	private WareHouseBOMModel wareHouseBOM;
	private float num;
	
	public WareHouseDeliveryBOMModel() {
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

	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}

	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
	}

	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}
}
