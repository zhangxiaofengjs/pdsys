package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.services.OrderService;
import com.zworks.pdsys.services.PnService;

@Controller
@RequestMapping("/pn")
public class PnController {
	@Autowired
	PnService pnService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 品目一览
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public JSONResponse listJson(PnModel pn, Model model) {
		List<PnModel> list = pnService.queryList(pn);
		return JSONResponse.success().put("pns", list);
	}
	
	/**
	 * 查询品目
	 * */
	@RequestMapping(value="/get")
	@ResponseBody
    public JSONResponse get(@RequestBody PnModel pn) {
		PnModel retPn = pnService.queryOne(pn);
		return JSONResponse.success().put("pn", retPn);
    }

	/**
	 * 添加品目
	 * */
	@RequestMapping(value="/add")
	@ResponseBody
    public JSONResponse add(@RequestBody PnModel pn) {
		if(pnService.exists(pn)) {
			return JSONResponse.error("已经存在品目:" + pn.getPn() + " " + pn.getName());
		}
		pnService.add(pn);
		return JSONResponse.success();
    }
	
	/**
	 * 修改品目
	 * */
	@RequestMapping(value="/update")
	@ResponseBody
    public JSONResponse update(@RequestBody PnModel pn) {
		pnService.update(pn);
		return JSONResponse.success();
    }
	
	/**
	 * 添加子类
	 * */
	@RequestMapping(value="/addpncls")
	@ResponseBody
    public JSONResponse addPnCls(@RequestBody PnModel pn) {
		if(pnService.existsPnCls(pn)) {
			return JSONResponse.error("已经存在该子类");
		}
		pnService.addPnCls(pn);
		return JSONResponse.success();
    }
	
	/**
	 * 修改子类
	 * */
	@RequestMapping(value="/updatepncls")
	@ResponseBody
	public JSONResponse updatePnCls(@RequestBody PnModel pn) {
		try {
			pnService.updatePnCls(pn);
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
		
		return JSONResponse.success();
	}
	
	/**
	 * 删除子类
	 * */
	@RequestMapping(value="/deletepncls")
	@ResponseBody
	public JSONResponse deletePnCls(@RequestBody PnModel pn) {
		try {
			pnService.deletePnCls(pn);
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
		
		return JSONResponse.success();
	}
	
	/**
	 * 添加原包材
	 * */
	@RequestMapping(value="/addBOM")
	@ResponseBody
	public JSONResponse addBOM(@RequestBody PnModel pn) {
		if(pnService.existsBOM(pn)) {
			//已经存在该原包材 则做更新
			return JSONResponse.error("已经存在该原包材");
		}
		pnService.addBOM(pn);
		return JSONResponse.success();
	}
	
	/**
	 * 删除原包材
	 * */
	@RequestMapping(value="/deleteBOM")
	@ResponseBody
	public JSONResponse deleteBOM(@RequestBody PnModel pn) {
		pnService.deleteBOM(pn);
		return JSONResponse.success();
	}
}
