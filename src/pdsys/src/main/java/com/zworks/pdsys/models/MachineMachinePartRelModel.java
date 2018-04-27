package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/09
 */
@Alias("machineMachinePartRelModel")
public class MachineMachinePartRelModel extends BaseModel {
	private float maitenacePartNum;
	private MachinePartModel machinePart;

	public MachinePartModel getMachinePart() {
		return machinePart;
	}

	public void setMachinePart(MachinePartModel machinePart) {
		this.machinePart = machinePart;
	}

	public float getMaitenacePartNum() {
		return maitenacePartNum;
	}

	public void setMaitenacePartNum(float maitenacePartNum) {
		this.maitenacePartNum = maitenacePartNum;
	}
}
