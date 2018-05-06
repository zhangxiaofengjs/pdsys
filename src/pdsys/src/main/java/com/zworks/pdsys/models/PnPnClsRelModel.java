package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/30
 */
@Alias("pnPnClsRelModel")
public class PnPnClsRelModel extends BaseModel{
	private PnClsModel pnCls;
	private float num;
	
	public PnClsModel getPnCls() {
		return pnCls;
	}

	public void setPnCls(PnClsModel pnCls) {
		this.pnCls = pnCls;
	}

	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}
}
