package com.zworks.pdsys.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zworks.pdsys.business.beans.NoticeFormBean;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.services.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/list")
    public Object showNoticelist(@RequestParam(value="pageon",defaultValue="1")int pageon,
    		                     NoticeFormBean formBean, ModelAndView mv) {
		
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
		
		Map<String, Object> map = noticeService.queryList(notice,pageon);
		map.put("formBean",formBean);
		
	    mv.addAllObjects(map);
	    mv.setViewName("notice/list");

        return mv;
    }
	
}
