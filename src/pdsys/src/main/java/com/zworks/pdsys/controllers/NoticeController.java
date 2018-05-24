package com.zworks.pdsys.controllers;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.beans.NoticeFormBean;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.services.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/list")
    public Object showNoticelist(@RequestParam(value="pageon",defaultValue="1")int pageon,
    		                     NoticeFormBean formBean, Model model) {
		
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
		
		notice.getFilterCond().put("start", s);
		notice.getFilterCond().put("end", e);

		//只显示发送给自己的
		notice.setReceiver(SecurityContextUtils.getLoginUser().getUser());

		List<NoticeModel> list = noticeService.queryList(notice);
		model.addAttribute("formBean",formBean);
		model.addAttribute("list",list);

        return "notice/list";
    }
	
	@RequestMapping(value="/toggleread")
	@ResponseBody
    public JSONResponse toggleRead(@RequestBody NoticeModel notice, Model model) {
		noticeService.toggleRead(notice);
		return JSONResponse.success();
    }
}
