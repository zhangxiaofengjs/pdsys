package com.zworks.pdsys.business.form.beans;

import java.util.Date;

import com.zworks.pdsys.common.utils.DateUtils;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/04
 */
public class WareHouseHistoryFormBean {
	private int bomType = -1;
	private String pn;
	private Date start;
	private Date end;
	
	//@NormalizeDate(start=true)
	//@SessionAttributeValue(init=true, daysOffset=0)
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = DateUtils.startOfDay(start);
	}
	
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = DateUtils.endOfDay(end);
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public int getBomType() {
		return bomType;
	}

	public void setBomType(int bomType) {
		this.bomType = bomType;
	}
}
