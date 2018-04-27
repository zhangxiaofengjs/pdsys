package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/18
 */
@Alias("pnMachineModel")
public class PnMachineModel extends BaseModel{
	private PnModel pn;
	private MachineModel machine;
	private float produceNum;
	public PnModel getPn() {
		return pn;
	}
	public void setPn(PnModel pn) {
		this.pn = pn;
	}
	public MachineModel getMachine() {
		return machine;
	}
	public void setMachine(MachineModel machine) {
		this.machine = machine;
	}
	public float getProduceNum() {
		return produceNum;
	}
	public void setProduceNum(float produceNum) {
		this.produceNum = produceNum;
	}
}
