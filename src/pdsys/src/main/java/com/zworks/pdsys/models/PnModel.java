package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("pnModel")
public class PnModel extends BaseModel{
	private String pn;
	private String name;
	private UnitModel unit;
	private List<PnPnClsRelModel> pnClsRels;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PnPnClsRelModel> getPnClsRels() {
		return pnClsRels;
	}

	public void setPnClsRels(List<PnPnClsRelModel> pnClsRels) {
		this.pnClsRels = pnClsRels;
	}
}
