package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zworks.pdsys.business.beans.SysMasterFormBean;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.CustomerModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.CustomerService;
import com.zworks.pdsys.services.MachineService;
import com.zworks.pdsys.services.PlaceService;
import com.zworks.pdsys.services.PnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/09
 */
@Controller
@RequestMapping("/sys/master")
public class MasterController {
	@Autowired
	PlaceService placeService;
	@Autowired
	CustomerService customerService;
	@Autowired
	BOMService bOMService;
	@Autowired
	PnService pnService;
	@Autowired
	MachineService machineService;
	
	@RequestMapping(value= {"/main", "/main/{type}", "/main/{type}"})
    public String main(
    		@PathVariable(name="type" ,required=false)String type,
    		SysMasterFormBean formBean,
    		Model model) {

		if(type == null) {
			type = RequestContextUtils.getSessionAttribute(this, "type", "customer");
		} else if(!(type.equals("customer") || type.equals("bom") || 
				type.equals("pn") || type.equals("machine")|| type.equals("fileinput"))) {
			throw new PdsysException("错误参数:/sys/master/main/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		RequestContextUtils.setSessionAttribute(this, "type", type);
		
		if(type.equals("customer")) {
			model.addAttribute("list", customerService.queryList(new CustomerModel()));
		} else if(type.equals("bom")) {
			BOMModel bom = formBean.getBom();
			if(bom == null) {
				bom = RequestContextUtils.getSessionAttribute(this, "bom", new BOMModel());
				formBean.setBom(bom);
			}
			bom.getFilterCond().put("fuzzyPnSearch", true);
			
			model.addAttribute("list", bOMService.queryList(bom));
			RequestContextUtils.setSessionAttribute(this, "bom", bom);
		} else if(type.equals("pn")) {
			PnModel pn = formBean.getPn();
			if(pn == null) {
				pn = RequestContextUtils.getSessionAttribute(this, "pn", new PnModel());
				formBean.setPn(pn);
			}
			pn.getFilterCond().put("fuzzyPnSearch", true);
			model.addAttribute("list", pnService.queryList(pn));
			RequestContextUtils.setSessionAttribute(this, "pn", pn);
		} else if(type.equals("machine")) {
			MachineModel m = formBean.getMachine();
			if(m == null) {
				m = RequestContextUtils.getSessionAttribute(this, "machine", new MachineModel());
				formBean.setMachine(m);
			}
			m.getFilterCond().put("fuzzyPnSearch", true);
			model.addAttribute("list", machineService.queryList(m));
		}
		model.addAttribute("formBean", formBean);
		model.addAttribute("type", type);
		return "sys/master/main";
    }
}
