package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("BOMModel")
public class BOMModel extends BaseModel {
	private String pn;
	private String name;
	private int type = -1;
	private float price;
	private UnitModel unit;
	private List<SupplierModel> suppliers;
	private WareHouseBOMModel whbom;

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

	public WareHouseBOMModel getWhbom() {
		return whbom;
	}

	public void setWhbom(WareHouseBOMModel whbom) {
		this.whbom = whbom;
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
}
