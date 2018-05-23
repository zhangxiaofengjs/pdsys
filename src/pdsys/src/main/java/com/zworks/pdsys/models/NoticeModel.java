package com.zworks.pdsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("noticeModel")
public class NoticeModel extends BaseModel {
	private DeviceModel device;
	private String comment;
	private String jobName;
	private Date createDate;
	public DeviceModel getDevice() {
		return device;
	}
	public void setDevice(DeviceModel device) {
		this.device = device;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
