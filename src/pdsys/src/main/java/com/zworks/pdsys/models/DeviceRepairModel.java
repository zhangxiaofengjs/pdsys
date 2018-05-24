package com.zworks.pdsys.models;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zworks.pdsys.common.utils.DateJsonDeserializer;
import com.zworks.pdsys.common.utils.DateJsonSerializer;

/**
 * 设备维修
 * 
 * @author ZHAI
 * @date 2018-03-30 13:22:06
 */
@Alias("deviceRepairModel")
public class DeviceRepairModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private DeviceModel device;
	private MachineTroubleModel machineTrouble;
    @JsonSerialize(using=DateJsonSerializer.class)
    @JsonDeserialize(using=DateJsonDeserializer.class)
	private Date repairDate;
	private String comment;
	public DeviceModel getDevice() {
		return device;
	}
	public void setDevice(DeviceModel device) {
		this.device = device;
	}
	public MachineTroubleModel getMachineTrouble() {
		return machineTrouble;
	}
	public void setMachineTrouble(MachineTroubleModel machineTrouble) {
		this.machineTrouble = machineTrouble;
	}
	public Date getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
