package com.zworks.pdsys.business.beans;

import java.util.Date;

import com.zworks.pdsys.common.utils.DateUtils;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/04
 */
public class WareHouseHistoryFormBean {
	private Date start;
	private Date end;
	
	public void normalizeStartEnd() {
		start = DateUtils.startOfDay(start);
		end = DateUtils.endOfDay(end);
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
