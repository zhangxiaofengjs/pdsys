package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Alias("wareHouseSemiPnModel")
public class WareHouseSemiPnModel extends BaseModel{
	private PnModel pn;
	private PnPnClsRelModel pnClsRel;
	private float num;

	public PnModel getPn() {
		return pn;
	}

	public void setPn(PnModel pn) {
		this.pn = pn;
	}

	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public PnPnClsRelModel getPnClsRel() {
		return pnClsRel;
	}

	public void setPnClsRel(PnPnClsRelModel pnClsRel) {
		this.pnClsRel = pnClsRel;
	}
}
