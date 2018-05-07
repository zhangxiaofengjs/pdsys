package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("wareHousePnModel")
public class WareHousePnModel extends BaseModel{
	private PnModel pn;
	private float producedNum;
	private float semiProducedNum;
	
	public float getProducedNum() {
		return producedNum;
	}

	public void setProducedNum(float producedNum) {
		this.producedNum = producedNum;
	}

	public float getSemiProducedNum() {
		return semiProducedNum;
	}

	public void setSemiProducedNum(float semiProducedNum) {
		this.semiProducedNum = semiProducedNum;
	}

	public PnModel getPn() {
		return pn;
	}

	public void setPn(PnModel pn) {
		this.pn = pn;
	}
}
