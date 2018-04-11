package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/09
 */
@Alias("machinePartModel")
public class MachinePartModel extends BaseModel {
	public static final MachinePartModel Empty = new MachinePartModel();
	private String pn;
	private String name;
	private UnitModel unit;
	private SupplierModel supplier;
	private List<MachineMachinePartRelModel> machineMachinePartRels;
	
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

	public List<MachineMachinePartRelModel> getMachineMachinePartRels() {
		return machineMachinePartRels;
	}

	public void setMachineMachinePartRels(List<MachineMachinePartRelModel> machineMachinePartRels) {
		this.machineMachinePartRels = machineMachinePartRels;
	}
}
