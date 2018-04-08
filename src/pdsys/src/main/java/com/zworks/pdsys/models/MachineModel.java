package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Alias("machineModel")
public class MachineModel extends BaseModel {
	private String pn;
	private String name;
	private UnitModel unit;
	private float maitenacePeriod;
	private SupplierModel supplier;

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UnitModel getUnit() {
		return unit;
	}

	public void setUnit(UnitModel unit) {
		this.unit = unit;
	}

	public SupplierModel getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierModel supplier) {
		this.supplier = supplier;
	}

	public float getMaitenacePeriod() {
		return maitenacePeriod;
	}

	public void setMaitenacePeriod(float maitenacePeriod) {
		this.maitenacePeriod = maitenacePeriod;
	}
}
