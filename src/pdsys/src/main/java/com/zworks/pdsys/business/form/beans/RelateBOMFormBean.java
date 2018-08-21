package com.zworks.pdsys.business.form.beans;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/21
 */
public class RelateBOMFormBean {
	private String type;
	private int PnId;
	private int pnClsId;
	private int bomId;
	private int newBomId;
	private float useNum;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPnId() {
		return PnId;
	}
	public void setPnId(int pnId) {
		PnId = pnId;
	}
	public int getPnClsId() {
		return pnClsId;
	}
	public void setPnClsId(int pnClsId) {
		this.pnClsId = pnClsId;
	}
	public int getBomId() {
		return bomId;
	}
	public void setBomId(int bomId) {
		this.bomId = bomId;
	}
	public int getNewBomId() {
		return newBomId;
	}
	public void setNewBomId(int newBomId) {
		this.newBomId = newBomId;
	}
	public float getUseNum() {
		return useNum;
	}
	public void setUseNum(float useNum) {
		this.useNum = useNum;
	}
}
