package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/31
 */
@Alias("pnClsModel")
public class PnClsModel extends BaseModel{
	private String name;
	private List<PnClsBOMRelModel> pnClsBOMRels;
	private UnitModel unit;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PnClsBOMRelModel> getPnClsBOMRels() {
		return pnClsBOMRels;
	}

	public void setPnClsBOMRels(List<PnClsBOMRelModel> pnClsBOMRels) {
		this.pnClsBOMRels = pnClsBOMRels;
	}

	public UnitModel getUnit() {
		return unit;
	}

	public void setUnit(UnitModel unit) {
		this.unit = unit;
	}
}
