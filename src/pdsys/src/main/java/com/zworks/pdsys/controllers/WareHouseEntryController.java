package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntryService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
@RequestMapping("/warehouse/entry")
public class WareHouseEntryController {
	@Autowired
	WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	WareHouseEntryService wareHouseEntryService;
	
	@RequestMapping(value= {"/main", "/main/{type}"})
    public String entryMain(
    		@PathVariable(name="type" ,required=false)String type,
    		@RequestParam(name="id", required=false)Integer id,
    		Model model) {

		if(type == null) {
			type = "pn";
		} else if(!(type.equals("bom") || type.equals("pn") || type.equals("machinepart"))) {
			throw new PdsysException("错误参数:/entry/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		
		WareHouseEntryModel entry;
		if(id != null) {
			entry = wareHouseEntryService.queryOne(id);
			if(entry == null) {
				throw new PdsysException("错误参数:/entry/type/id=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
			}
		} else {
			entry = new WareHouseEntryModel();
		}
		
		model.addAttribute("entry", entry);
		model.addAttribute("type", type);
		return "warehouse/entry/main";
    }
	
	/**
	 * 新建入库单
	 * */
	@RequestMapping(value="/add/entry")
	@ResponseBody
    public JSONResponse addEntry(@RequestBody WareHouseEntryModel entry, Model model) {
		wareHouseEntryService.add(entry);
		return JSONResponse.success().put("id", entry.getId());
    }
	
	/**
	 * 新建入库明细
	 * */
	@RequestMapping(value="/update/pn")
	@ResponseBody
    public JSONResponse updateEntry(@RequestBody WareHouseEntryPnModel entryPn, Model model) {
		if(wareHouseEntryPnService.exist(entryPn)) {
			wareHouseEntryPnService.update(entryPn);
		} else {
			wareHouseEntryPnService.add(entryPn);
		}
		return JSONResponse.success();
    }
	
	/**
	 * 删除入库明细
	 * */
	@RequestMapping(value="/delete/pn")
	@ResponseBody
    public JSONResponse addEntryPn(@RequestBody List<WareHouseEntryPnModel> entryPns, Model model) {
		wareHouseEntryPnService.delete(entryPns);
		return JSONResponse.success();
    }
	
	/**
	 * 删除入库单
	 * */
	@RequestMapping(value="/delete/entry/{id}")
	@ResponseBody
	public JSONResponse deleteEntry(@PathVariable(name="id") Integer id, Model model) {
		WareHouseEntryModel entry = wareHouseEntryService.queryOne(id);
		if(entry == null) {
			throw new PdsysException("错误参数:/entry/delete/entry/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM); 
		}
		wareHouseEntryService.delete(entry);
		return JSONResponse.success();
	}
	
	/**
	 * 入库
	 * */
	@RequestMapping(value="/entry/{id}")
	@ResponseBody
    public JSONResponse delivery(@PathVariable(name="id", required=false)Integer id, Model model) {
		WareHouseEntryModel entry = wareHouseEntryService.queryOne(id);
		if(entry == null) {
			throw new PdsysException("错误参数:/entry/entry/id=" + id, PdsysExceptionCode.ERROR_REQUEST_PARAM); 
		}
		if(wareHouseEntryService.entry(entry)) {
			return JSONResponse.success();
		} else {
			return JSONResponse.error();
		}
    }
}
