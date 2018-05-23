package com.zworks.pdsys.business.beans;

import com.zworks.pdsys.models.PnClsModel;

public class SemiPnClsDetailBean {
	private PnClsModel pnCls;
	private float unitNum;//构成一个单位的PN需要的数量
	private float num;//拥有的数量
	
	public PnClsModel getPnCls() {
		return pnCls;
	}
	public void setPnCls(PnClsModel pnCls) {
		this.pnCls = pnCls;
	}
	public float getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(float unitNum) {
		this.unitNum = unitNum;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
}
