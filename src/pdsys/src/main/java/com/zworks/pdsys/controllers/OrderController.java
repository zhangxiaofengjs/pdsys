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
	
	@RequestMapping("/list/json")
	@ResponseBody
	public List<OrderModel> listJson(OrderModel order, Model model) {
		List<OrderModel> list = orderService.queryList(order);
		return list;
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
		return JSONResponse.success();
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
	 * 订单详细（跳转用）
	 */
	@RequestMapping("/detail")
	public String show(@RequestParam(name="id") int id, Model model) {
		OrderModel order = orderService.queryObject(id);
		model.addAttribute("order", order);

        return "order/detail";
    }
	
	/**
	 * 订单详细
	 */
	@RequestMapping("/detail/list")
	@ResponseBody
	public JSONResponse showOrderDetail(@RequestBody OrderModel order, Model model) {
		OrderPnModel orderPn = new OrderPnModel();
		OrderModel o = new OrderModel();
		o.setId(order.getId());
		orderPn.setOrder(o);
		
		List<OrderPnModel> list = orderService.queryOrderPnList(orderPn);

		o = orderService.queryObject(order.getId());
		model.addAttribute("order", o);

		return JSONResponse.success().put("orderDetail", list);
    }
	
	@RequestMapping("/plan")
    public String plan(Model model) {
		
        return "order/plan";
    }
	
	
	
	
	
	
	
	
	
	/**
	 * 取得订单项目
	 */
	@RequestMapping("/pn/list/json")
	@ResponseBody
	public List<OrderPnModel> pnListJson(@RequestBody OrderPnModel orderPn, Model model) {
		List<OrderPnModel> list = orderService.queryOrderPnList(orderPn);
		return list;
	}
}
