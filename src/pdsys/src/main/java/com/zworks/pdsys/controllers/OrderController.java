package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.enumClass.OrderState;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.ValidatorUtils;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderPnService orderPnService;
	
	@RequestMapping("/list")
    public String showOrderlist(OrderModel order, Model model) {
		
		//订单一览加载
		order.getFilterCond().put("fuzzyNoSearch", true);
		
		List<OrderModel> list = orderService.queryList(order);
		model.addAttribute("orders", list);
		model.addAttribute("order", order);
		
		//下拉列表加载
		OrderModel o =new OrderModel();
		list = orderService.queryList(o);
		int[] states = orderService.removeDuplicate(list);
		model.addAttribute("states", states);

        return "order/list";
    }
	
	@RequestMapping("/list/json")
	@ResponseBody
	public JSONResponse showOrderlistJson(@RequestBody OrderModel order) {
		List<OrderModel> list = orderService.queryList(order);
		return JSONResponse.success().put("orders", list);
	}
	
	/**
	 * 删除订单(非物理删除)
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JSONResponse updateOrderState(@RequestBody OrderModel order) {
		OrderModel o = orderService.queryOne(order);
		if(o == null) {
			return JSONResponse.error("已选订单不存在！");
		}

		o.setState(OrderState.DELETED.ordinal());
		orderService.updateOrderState(o);
		return JSONResponse.success("删除订单成功！");
	}
	
	/**
	 * 新增订单
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONResponse saveOrder(@RequestBody OrderModel order) {
		//验证处理
		JSONResponse JR = ValidatorUtils.doValidate(order);
		if( JR!=null )
			return JR;
		orderService.save(order);
		return JSONResponse.success("新增订单成功!");
	}
	
	/**
	 * 订单信息
	 */
	@RequestMapping("/detail")
	public String showOrderInfo(@RequestParam(name="id") int id, Model model) {
		OrderModel order = new OrderModel();
		order.setId(id);
		order = orderService.queryOne(order);
		model.addAttribute("order", order);
		
		List<OrderPnModel> list = null;
		if( order!=null )
		{
			list = orderPnService.queryOrderPnList(order);
		}
		model.addAttribute("orderPns", list);

        return "order/detail";
    }
}
