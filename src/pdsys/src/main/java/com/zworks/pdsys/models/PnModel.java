package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("pnModel")
public class PnModel {
	private int id;
	private String pn;
	private PnModel subPn;
	private UnitModel unit;

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public UnitModel getUnit() {
		return unit;
	}

	public void setUnit(UnitModel unit) {
		this.unit = unit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PnModel getSubPn() {
		return subPn;
	}

	public void setSubPn(PnModel subPn) {
		this.subPn = subPn;
	}
}
