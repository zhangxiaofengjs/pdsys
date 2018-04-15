package com.zworks.pdsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.services.PnClsService;

@Controller
@RequestMapping("/pncls")
public class PnClsController {
	@Autowired
	PnClsService pnClsService;
	
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
}
