package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.form.beans.NoticeFormBean;
import com.zworks.pdsys.common.annotations.PdSysLog;
import com.zworks.pdsys.common.enumClass.NoticeState;
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
		NoticeModel notice = formBean.getNotice();
		if(notice == null) {
			formBean = RequestContextUtils.getSessionAttribute(this, "formBean", new NoticeFormBean());
			if(formBean.getStart() == null) {
				formBean.setStart(DateUtils.thisMonthStart());
			}
			if(formBean.getEnd() == null) {
				formBean.setEnd(DateUtils.getCurrentDate());
			}
			if(formBean.getNotice() == null) {
				NoticeModel n = new NoticeModel();
				n.setState(0);
				formBean.setNotice(n);
			}
			notice = formBean.getNotice();
		}
		RequestContextUtils.setSessionAttribute(this, "formBean", formBean);

		notice.getFilterCond().put("start", DateUtils.startOfDay(formBean.getStart()));
		notice.getFilterCond().put("end", DateUtils.endOfDay(formBean.getEnd()));

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
		noticeService.toggleState(notice);
		return JSONResponse.success();
    }
	
	@RequestMapping(value="/getcount")
	@ResponseBody
	@PdSysLog(ignore=true)
    public JSONResponse getCount(Model model) {
		NoticeModel notice = new NoticeModel();
		notice.setState(NoticeState.Unread.ordinal());
		notice.setReceiver(SecurityContextUtils.getLoginUser().getUser());

		List<NoticeModel> list = noticeService.queryList(notice);
		return JSONResponse.success().put("count", list.size());
    }
}
