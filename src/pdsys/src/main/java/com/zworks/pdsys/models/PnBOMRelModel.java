package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/29
 */
@Alias("pnBOMRelModel")
public class PnBOMRelModel extends BaseModel {
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
