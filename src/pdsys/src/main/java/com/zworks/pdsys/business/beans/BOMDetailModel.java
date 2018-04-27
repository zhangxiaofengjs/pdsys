package com.zworks.pdsys.business.beans;

import org.apache.ibatis.type.Alias;

import com.zworks.pdsys.models.SupplierModel;

/**
 * @author: ZHAI
 */
@Alias("BOMDetailModel")
public class BOMDetailModel {
	
	//数量
	private float bomNum;
	
	//品名
	private String bomName;
	
	//分类
	private int bomType;
	
	//单位
	private String unitName;
	
	//库存量
	private int whbomNum;
	
	//制造商
	private SupplierModel supplier;

	public int getWhbomNum() {
		return whbomNum;
	}

	public void setWhbomNum(int whbomNum) {
		this.whbomNum = whbomNum;
	}

	public float getBomNum() {
		return bomNum;
	}

	public void setBomNum(float bomNum) {
		this.bomNum = bomNum;
	}

	public String getBomName() {
		return bomName;
	}

	public void setBomName(String bomName) {
		this.bomName = bomName;
	}

	public int getBomType() {
		return bomType;
	}

	public void setBomType(int bomType) {
		this.bomType = bomType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public SupplierModel getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierModel supplier) {
		this.supplier = supplier;
	}
	
}
