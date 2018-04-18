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
		
		List<OrderModel> list = orderService.queryList(order);
		model.addAttribute("orders", list);
		model.addAttribute("order", order);
		
        return "order/list";
    }
	
	@RequestMapping("/list/json")
	@ResponseBody
	public List<OrderModel> showOrderlistJson(OrderModel order) {
		List<OrderModel> list = orderService.queryList(order);
		return list;
	}
	
	/**
	 * 删除订单(非物理删除)
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JSONResponse updateOrderState(@RequestBody int[] ids) {
		for (int i=0; i<ids.length; i++)
		{
			OrderModel order = orderService.queryObject(ids[i]);
			if(order == null) {
				return JSONResponse.error("已选订单不存在！");
			}

			order.setState(OrderState.DELETED.ordinal());
			orderService.updateOrderState(order);
		}
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
	@RequestMapping("/orderInfo")
	public String showOrderInfo(@RequestParam(name="id") int id, Model model) {
		OrderModel order = orderService.queryObject(id);
		model.addAttribute("order", order);

        return "order/detail";
    }
	
	/**
	 * 生产计划
	 */
	@RequestMapping("/plan")
    public String plan(Model model) {
		
        return "order/plan";
    }

}
