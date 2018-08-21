package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.PnClsBusiness;
import com.zworks.pdsys.business.form.beans.RelateBOMFormBean;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.services.PnClsService;

@Controller
@RequestMapping("/pncls")
public class PnClsController {
	@Autowired
	PnClsService pnClsService;
	@Autowired
	PnClsBusiness pnClsBusiness;
	
	@RequestMapping("/list/json")
	@ResponseBody
	public List<PnClsModel> listJson(PnClsModel plCls, Model model) {
		List<PnClsModel> list = pnClsService.queryList(plCls);
		return list;
	}
	
	@RequestMapping(value="/add")
	@ResponseBody
    public JSONResponse add(@RequestBody PnClsModel pnCls) {
		if(pnClsService.exists(pnCls)) {
			return JSONResponse.error("已经存在该子类");
		}
		pnClsService.add(pnCls);
		return JSONResponse.success().put("pnCls", pnCls);
    }
	
	@RequestMapping(value="/addBOM")
	@ResponseBody
    public JSONResponse addBOM(@RequestBody PnClsModel pnCls) {
		try {
			pnClsBusiness.addBOM(pnCls);
		} catch(PdsysException e) {
			return JSONResponse.error(e.getMessage());
		}
		return JSONResponse.success();
    }
	@RequestMapping(value="/deleteBOM")
	@ResponseBody
	public JSONResponse deleteBOM(@RequestBody PnClsModel pnCls) {
		try {
			pnClsBusiness.deleteBOM(pnCls);
		} catch(PdsysException e) {
			return JSONResponse.error(e.getMessage());
		}
		return JSONResponse.success();
	}
	@RequestMapping(value="/changeBOM")
	@ResponseBody
	public JSONResponse changeBOM(@RequestBody RelateBOMFormBean formBean) {
		try {
			pnClsBusiness.changeBOM(formBean);
		} catch(PdsysException e) {
			return JSONResponse.error(e.getMessage());
		}
		return JSONResponse.success();
	}
}
