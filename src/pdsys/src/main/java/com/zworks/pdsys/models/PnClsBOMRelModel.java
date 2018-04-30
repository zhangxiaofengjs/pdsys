package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: ZHAI
 * @version: 2018/04/03
 */
@Alias("pnClsBOMRelModel")
public class PnClsBOMRelModel extends BaseModel {
	private BOMModel bom;
	private float useNum;

	public float getUseNum() {
		return useNum;
	}

	public void setUseNum(float useNum) {
		this.useNum = useNum;
	}

	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}
}
