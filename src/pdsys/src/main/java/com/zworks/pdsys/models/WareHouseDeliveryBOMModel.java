package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/02
 */
@Alias("wareHouseCheckoutBOMModel")
public class WareHouseDeliveryBOMModel extends BaseModel{
	public static final WareHouseDeliveryBOMModel Empty = new WareHouseDeliveryBOMModel();
	
	private WareHouseDeliveryModel wareHouseDelivery;
	private BOMModel bom;
	private float num;
	
	public WareHouseDeliveryBOMModel() {
		setWareHouseDelivery(WareHouseDeliveryModel.Empty);
		setBom(BOMModel.Empty);
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

	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}
}
