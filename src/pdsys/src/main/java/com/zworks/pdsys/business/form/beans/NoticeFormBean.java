package com.zworks.pdsys.business.form.beans;

import java.util.Date;

import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.models.NoticeModel;

public class NoticeFormBean {
	private Date start;
	private Date end;
	private NoticeModel notice;
	
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

	public NoticeModel getNotice() {
		return notice;
	}

	public void setNotice(NoticeModel notice) {
		this.notice = notice;
	}
}
