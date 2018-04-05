package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Alias("wareHouseMachinePartModel")
public class WareHouseMachinePartModel extends BaseModel{
	public static final WareHouseMachinePartModel Empty = new WareHouseMachinePartModel();
	
	private float num;
	private MachinePartModel machinePart;
	
	public WareHouseMachinePartModel() {
		this.setMachinePart(new MachinePartModel());
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
