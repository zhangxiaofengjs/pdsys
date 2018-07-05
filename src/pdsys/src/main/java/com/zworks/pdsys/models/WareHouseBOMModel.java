package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("wareHouseBOMModel")
public class WareHouseBOMModel extends BaseModel{
	private float num;
	private float deliveryRemainingNum;
	private BOMModel bom;
	
	public WareHouseBOMModel() {
		this.bom = new BOMModel();
	}
	
	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}

	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public float getDeliveryRemainingNum() {
		return deliveryRemainingNum;
	}

	public void setDeliveryRemainingNum(float deliveryRemainingNum) {
		this.deliveryRemainingNum = deliveryRemainingNum;
	}
}
