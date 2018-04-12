package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
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
	public List<PnModel> listJson() {
		List<PnModel> list = pnService.queryList();
		return list;
	}
	
	/**
	 * 当前品目下的子类一览
	 */
	@RequestMapping("/clsList/json")
	@ResponseBody
	public List<PnClsModel> pnClsListJson(@RequestBody PnModel pn) {
		List<PnClsModel> list = pnService.queryClsList(pn);
		return list;
	}
	
	/**
	 * 添加品目
	 * */
	@RequestMapping(value="/add/pn")
	@ResponseBody
    public JSONResponse save(@RequestBody OrderPnModel orderPn) {
		pnService.save(orderPn);
		return JSONResponse.success();
    }
	
	/**
	 * 删除品目
	 */
	@RequestMapping("/delete/pn")
	@ResponseBody
	public JSONResponse delete(@RequestBody List<OrderPnModel> orderPns) {
		for(OrderPnModel orderPn : orderPns) {
			pnService.delete(orderPn);
		}
		return JSONResponse.success();
	}
	
	/**
	 * BOM详细
	 */
	@RequestMapping("/bomInfo/list")
    public String list(@RequestParam(name="id") int id, Model model) {
		OrderModel order = orderService.queryObject(id);
		if( order!=null )
		{
			List<BomDetailModel> list = pnService.queryBomList(order);
			model.addAttribute("boms", list);
			model.addAttribute("order", order);
		}
		
        return "order/bomDetail";
    }

}
