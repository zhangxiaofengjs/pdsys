package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Alias("pnModel")
public class PnModel extends BaseModel{
	public static final PnModel Empty = new PnModel();
	
	private String pn;
	private String name;
	private PnClsModel pnCls;
	private UnitModel unit;

	public PnModel() {
		setPnCls(PnClsModel.Empty);
		unit = UnitModel.Empty;
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
}
