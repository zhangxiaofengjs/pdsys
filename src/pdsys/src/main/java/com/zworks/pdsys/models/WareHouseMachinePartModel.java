package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Alias("wareHouseMachinePartModel")
public class WareHouseMachinePartModel extends BaseModel{
	private float num;
	private float defectiveNum;
	private float scrapNum;
	private MachinePartModel machinePart;
	
	public WareHouseMachinePartModel() {
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public MachinePartModel getMachinePart() {
		return machinePart;
	}

	public void setMachinePart(MachinePartModel machinePart) {
		this.machinePart = machinePart;
	}

	public float getDefectiveNum() {
		return defectiveNum;
	}

	public void setDefectiveNum(float defectiveNum) {
		this.defectiveNum = defectiveNum;
	}

	public float getScrapNum() {
		return scrapNum;
	}

	public void setScrapNum(float scrapNum) {
		this.scrapNum = scrapNum;
	}
}
