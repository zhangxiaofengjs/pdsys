package com.zworks.pdsys.business.beans;

import org.apache.ibatis.type.Alias;

import com.zworks.pdsys.models.BOMModel;

/**
 * @author: ZHAI
 */
@Alias("bomUseNumBean")
public class BOMUseNumBean {
	private BOMModel bom;
	//待生产订单总需量
	private float useNum;
	//在库数量
	private float wareHouseNum;
	//已下单采购量
	private float purchasedNum;
		
	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}

	public float getUseNum() {
		return useNum;
	}

	public void setUseNum(float useNum) {
		this.useNum = useNum;
	}

	public float getWareHouseNum() {
		return wareHouseNum;
	}

	public void setWareHouseNum(float wareHouseNum) {
		this.wareHouseNum = wareHouseNum;
	}

	public float getPurchasedNum() {
		return purchasedNum;
	}

	public void setPurchasedNum(float purchasedNum) {
		this.purchasedNum = purchasedNum;
	}
}
