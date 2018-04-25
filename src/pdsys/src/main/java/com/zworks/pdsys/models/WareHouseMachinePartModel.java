package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Alias("wareHouseMachinePartModel")
public class WareHouseMachinePartModel extends BaseModel{
	private float num;
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
}
