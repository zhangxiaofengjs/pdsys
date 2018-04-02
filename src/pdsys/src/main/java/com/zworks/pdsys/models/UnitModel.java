package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("unitModel")
public class UnitModel extends BaseModel{
	public static final UnitModel Empty = new UnitModel();
	
	private String name;
	private UnitModel subUnit;
	private float ratio;

	public String getName() {
		return name;
	}

	public UnitModel getSubUnit() {
		return subUnit;
	}

	public void setSubUnit(UnitModel subUnit) {
		this.subUnit = subUnit;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public void setName(String name) {
		this.name = name;
	}
}
