package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.ValidatorUtils;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.OrderService;

@Controller
@RequestMapping("/orderPn")
public class OrderPnController {
	
	@Autowired
	OrderPnService orderPnService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 取得订单品目
	 */
	@RequestMapping("/list")
	public String list(OrderPnModel orderPn, Model model) {
		//订单一览加载
		orderPn.putFilterCond(OrderPnModel.FCK_FUZZYPNSEARCH, true);
		orderPn.putFilterCond(OrderPnModel.FCK_NOTDELIVERD, true);
		orderPn.putFilterCond(OrderPnModel.FCK_ORDERBYPN, true);

		List<OrderPnModel> list = orderPnService.queryList(orderPn);
		model.addAttribute("orderPns", list);
		model.addAttribute("orderPn", orderPn);

		return "order/listpn";
	}
	
	/**
	 * 取得订单品目
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public JSONResponse listJson(@RequestBody OrderPnModel orderPn) {
		List<OrderPnModel> list = orderPnService.queryList(orderPn);
		return JSONResponse.success().put("orderPns", list);
	}
	
	/**
	 * 添加品目订单详细
	 * */
	@RequestMapping(value="/add")
	@ResponseBody
    public JSONResponse saveOrderDetail(@RequestBody OrderPnModel orderPn) {
		//验证处理
		JSONResponse JR = ValidatorUtils.doValidate(orderPn);
		if( JR!=null )
			return JR;
		
		boolean isExist = orderPnService.existsOrderPn(orderPn);
		if( isExist )
			return JSONResponse.error("该品目已经存在！");

		orderPnService.add(orderPn);
		return JSONResponse.success();
    }
	
	/**
	 * 删除品目订单
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JSONResponse delete(@RequestBody List<OrderPnModel> orderPns) {
		for(OrderPnModel orderPn : orderPns) {
			orderPnService.delete(orderPn);
		}
		return JSONResponse.success();
	}
	
	/**
	 * 通过订单详细的ID取得品目
	 */
	@RequestMapping("/get")
	@ResponseBody
	public JSONResponse get(@RequestBody OrderPnModel orderPn ){
		OrderPnModel op = orderPnService.queryOne( orderPn );
		if(op == null) {
			return JSONResponse.error("不存在的订单品目");
		}
		return JSONResponse.success().put("orderPn", op);
	}
	
	/**
	 * 更新订单详细的ID取得品目
	 */
	@RequestMapping("/update")
	@ResponseBody
	public JSONResponse updateOrderPnInfo(@RequestBody OrderPnModel orderPn) {
		//验证处理
		JSONResponse JR = ValidatorUtils.doValidate(orderPn);
		if( JR!=null )
			return JR;
		
		orderPn.getFilterCond().put("update_num", true);
		orderPn.getFilterCond().put("update_price", true);
		orderPnService.update(orderPn);
		return JSONResponse.success();
	}

}
