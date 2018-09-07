package com.zworks.pdsys.business.form.beans;

import com.zworks.pdsys.common.annotations.PdSysSessionDefaultValue;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
public class WareHouseListFormBean {
	@PdSysSessionDefaultValue("pn")
	private String type;

	private String pnPn;
	private String bomPn;
	private String semipnPn;
	private String machinePartPn;
	
	public WareHouseListFormBean() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPnPn() {
		return pnPn;
	}

	public void setPnPn(String pnPn) {
		this.pnPn = pnPn;
	}

	public String getBomPn() {
		return bomPn;
	}

	public void setBomPn(String bomPn) {
		this.bomPn = bomPn;
	}

	public String getSemipnPn() {
		return semipnPn;
	}

	public void setSemipnPn(String semipnPn) {
		this.semipnPn = semipnPn;
	}

	public String getMachinePartPn() {
		return machinePartPn;
	}

	public void setMachinePartPn(String machinePartPn) {
		this.machinePartPn = machinePartPn;
	}
}
