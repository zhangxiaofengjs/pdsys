package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.enumClass.OrderState;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/list")
    public String list(OrderModel order, Model model) {
		
		List<OrderModel> list = orderService.queryList(order);
		model.addAttribute("orders", list);
		model.addAttribute("order", order);
		
        return "order/list";
    }
	
	/**
	 * 删除订单(非物理删除)
	 * @param JSONResponse 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JSONResponse delete(@RequestBody int[] ids) {
		for (int i=0; i<ids.length; i++)
		{
			OrderModel order = orderService.queryObject(ids[i]);
			if( order!= null )
			{
				order.setState(OrderState.DELETED.ordinal());
				orderService.delete(order);
			}
		}
		return JSONResponse.success("删除订单成功!");
	}
	
	/**
	 * 新增订单
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONResponse save(@RequestBody OrderModel order) {
		orderService.save(order);
		return JSONResponse.success("新增订单成功!");
	}
	
	/**
	 * 订单详细
	 */
	@RequestMapping("/detail")
	public String showOrderDetail(OrderModel order, Model model) {
		List<OrderPnModel> list = orderService.showOrderDetail(order);
		model.addAttribute("orderDetail", list);
		int orderId = order.getId();
		order = orderService.queryObject(orderId);
		model.addAttribute("order", order);
		
        return "order/detail";
    }
}
