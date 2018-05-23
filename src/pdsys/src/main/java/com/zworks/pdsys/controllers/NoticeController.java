package com.zworks.pdsys.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zworks.pdsys.business.beans.NoticeFormBean;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.services.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/list")
    public String showNoticelist(NoticeFormBean formBean, Model model) {
		
		NoticeModel notice = new NoticeModel();
		Date s = DateUtils.startOfDay(formBean.getStart());
		Date e = DateUtils.endOfDay(formBean.getEnd());
		if(s == null) {
			s = RequestContextUtils.getSessionAttribute(this, "startNotice", DateUtils.startOfDay(DateUtils.thisMonthStart()));
			formBean.setStart(s);
		}
		RequestContextUtils.setSessionAttribute(this, "startNotice", s);
		if(e == null) {
			e = RequestContextUtils.getSessionAttribute(this, "endNotice", DateUtils.endOfDay(DateUtils.now()));
			formBean.setEnd(e);
		}
		RequestContextUtils.setSessionAttribute(this, "endNotice", e);
		
		notice.getFilterCond().put("noticeStart", s);
		notice.getFilterCond().put("noticeEnd", e);
		
		List<NoticeModel> list = noticeService.queryList(notice);
		model.addAttribute("notices", list);
		model.addAttribute("formBean",formBean);

        return "notice/list";
    }
	
}
