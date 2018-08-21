package com.zworks.pdsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("pnModel")
public class PnModel extends BaseModel{
	public static final String FCK_PNCLSID = "pnClsId";
	
	private String pn;
	private String name;
	private UnitModel unit;
	private float price;
	private UnitModel priceUnit;
	private List<PnPnClsRelModel> pnClsRels;
	private List<PnBOMRelModel> pnBOMRels;

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

	public List<PnBOMRelModel> getPnBOMRels() {
		return pnBOMRels;
	}

	public void setPnBOMRels(List<PnBOMRelModel> pnBOMRels) {
		this.pnBOMRels = pnBOMRels;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public UnitModel getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(UnitModel priceUnit) {
		this.priceUnit = priceUnit;
	}
}
