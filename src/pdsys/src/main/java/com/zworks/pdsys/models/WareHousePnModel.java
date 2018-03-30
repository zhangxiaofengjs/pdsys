package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("wareHousePnModel")
public class WareHousePnModel {
	public static final WareHousePnModel Empty = new WareHousePnModel();
	
	private int id;
	private float num;
	private PnModel pn;
	
	public WareHousePnModel() {
		this.setPn(new PnModel());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public PnModel getPn() {
		return pn;
	}

	public void setPn(PnModel pn) {
		this.pn = pn;
	}
}
