package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/25
 */
@Alias("wareHouseEntryMachinePartModel")
public class WareHouseEntryMachinePartModel extends BaseModel{
	private WareHouseEntryModel wareHouseEntry;
	private WareHouseMachinePartModel wareHouseMachinePart;
	private MachinePartModel machinePart;
	private float num;
	
	public WareHouseEntryModel getWareHouseEntry() {
		return wareHouseEntry;
	}
	public void setWareHouseEntry(WareHouseEntryModel wareHouseEntry) {
		this.wareHouseEntry = wareHouseEntry;
	}
	public WareHouseMachinePartModel getWareHouseMachinePart() {
		return wareHouseMachinePart;
	}
	public void setWareHouseMachinePart(WareHouseMachinePartModel wareHouseMachinePart) {
		this.wareHouseMachinePart = wareHouseMachinePart;
	}
	public MachinePartModel getMachinePart() {
		return machinePart;
	}
	public void setMachinePart(MachinePartModel machinePart) {
		this.machinePart = machinePart;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
}
