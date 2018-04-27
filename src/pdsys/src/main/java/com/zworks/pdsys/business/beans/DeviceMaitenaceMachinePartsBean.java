package com.zworks.pdsys.business.beans;

import java.util.List;

import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/09
 */
public class DeviceMaitenaceMachinePartsBean {
	private MachinePartModel machinePart;
	private List<MachineModel> machines;
	private float maitenaceNum;
	private float wareHouseNum;
	
	public DeviceMaitenaceMachinePartsBean() {
	}

	public MachinePartModel getMachinePart() {
		return machinePart;
	}

	public void setMachinePart(MachinePartModel machinePart) {
		this.machinePart = machinePart;
	}

	public float getMaitenaceNum() {
		return maitenaceNum;
	}

	public void setMaitenaceNum(float maitenaceNum) {
		this.maitenaceNum = maitenaceNum;
	}

	public float getWareHouseNum() {
		return wareHouseNum;
	}

	public void setWareHouseNum(float wareHouseNum) {
		this.wareHouseNum = wareHouseNum;
	}

	public List<MachineModel> getMachines() {
		return machines;
	}

	public void setMachines(List<MachineModel> machines) {
		this.machines = machines;
	}
}
