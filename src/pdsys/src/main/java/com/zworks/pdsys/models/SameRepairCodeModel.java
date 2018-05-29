package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhai
 */
public class SameRepairCodeModel extends BaseModel {
	private String pn;
	private String name;
	private String comment;
	private int type = -1;
	private float price;
	private UnitModel unit;
	private List<SupplierModel> suppliers;
	private WareHouseBOMModel wareHouseBOM;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<SupplierModel> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<SupplierModel> suppliers) {
		this.suppliers = suppliers;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}

	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
	}
}
