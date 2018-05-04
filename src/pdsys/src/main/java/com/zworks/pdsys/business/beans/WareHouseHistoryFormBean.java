package com.zworks.pdsys.business.beans;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zworks.pdsys.common.utils.DateJsonDeserializer;
import com.zworks.pdsys.common.utils.DateJsonSerializer;
import com.zworks.pdsys.common.utils.DateUtils;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/04
 */
public class WareHouseHistoryFormBean {
	private Date start = DateUtils.startOfDay(DateUtils.thisMonthStart());
	private Date end= DateUtils.endOfDay(DateUtils.now());
	
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
