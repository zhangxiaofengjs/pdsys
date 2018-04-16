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
	private PnClsModel pnCls;
	private UnitModel unit;
	private PnBOMRelModel bomRel;
	private List<BOMModel> boms;
	private List<PnClsModel> pnClss;//TODO 考虑将成员去除：pnCls，boms，同时bomRel似乎不合理
	private List<PnBOMRelModel> bomRels;

	public PnModel() {
	}
	
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

	public PnClsModel getPnCls() {
		return pnCls;
	}

	public void setPnCls(PnClsModel pnCls) {
		this.pnCls = pnCls;
	}

	public List<BOMModel> getBoms() {
		return boms;
	}

	public void setBoms(List<BOMModel> boms) {
		this.boms = boms;
	}

	public PnBOMRelModel getBomRel() {
		return bomRel;
	}

	public void setBomRel(PnBOMRelModel bomRel) {
		this.bomRel = bomRel;
	}

	public List<PnClsModel> getPnClss() {
		return pnClss;
	}

	public void setPnClss(List<PnClsModel> pnClss) {
		this.pnClss = pnClss;
	}

	public List<PnBOMRelModel> getBomRels() {
		return bomRels;
	}

	public void setBomRels(List<PnBOMRelModel> bomRels) {
		this.bomRels = bomRels;
	}
}
