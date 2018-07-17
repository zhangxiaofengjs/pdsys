package com.zworks.pdsys.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.enumClass.OrderState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.common.utils.ValidatorUtils;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderPnService orderPnService;
	
	@RequestMapping("/list")
    public String showOrderlist(OrderModel order, Model model) {
		if(order.getState() == -1) {
			order.setState(OrderState.PRODUCTING.ordinal());//默认显示生产中
		}
		//订单一览加载
		order.getFilterCond().put("fuzzyNoSearch", true);
		
		List<OrderModel> list = orderService.queryList(order);
		model.addAttribute("orders", list);
		model.addAttribute("order", order);

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

		if(!SecurityContextUtils.isLoginUser(o.getUser())) {
			return JSONResponse.error("登录用户不是订单责任者！");
		}

		o.setState(OrderState.DELETED.ordinal());
		o.getFilterCond().put("update_state", true);
		orderService.update(o);
		return JSONResponse.success("删除订单成功！");
	}
	
	/**
	 * 修改订单
	 */
	@RequestMapping("/update")
	@ResponseBody
	public JSONResponse updateOrder(@RequestBody OrderModel order) {
		OrderModel om = new OrderModel();
		om.setId(order.getId());
		
		OrderModel o = orderService.queryOne(om);
		if(o == null) {
			return JSONResponse.error("已选订单不存在！");
		}
		else if( o.getState() == OrderState.DELETED.ordinal() )
		{
			return JSONResponse.error("状态为已删除的订单不能修改！");
		}

		order.getFilterCond().put("update_state", true);
		order.getFilterCond().put("update_ship_dead_date", true);
		order.getFilterCond().put("update_comment", true);
		
		orderService.update(order);
		return JSONResponse.success("修改订单成功！");
	}
	
	/**
	 * 修改订单
	 */
	@RequestMapping("/import")
	@ResponseBody
	public JSONResponse importFile(@RequestParam("file") MultipartFile[] files) {
		try {
			orderService.importFile(files);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/attachment/add")
	@ResponseBody
	public JSONResponse addAttachment(@RequestParam("id") int id, @RequestParam("file") MultipartFile[] files) {
		try {
			orderService.addAttachment(id, files);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/attachment/delete")
	@ResponseBody
	public JSONResponse deleteAttachment(@RequestBody OrderModel order) {
		try {
			orderService.deleteAttachment(order);
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/attachment/download")
	public void downloadAttachment(int orderId, HttpServletRequest request, HttpServletResponse response) {
		try {
			orderService.downloadAttachment(orderId, response);
			//return JSONResponse.success();
		} catch(PdsysException ex) {
			//return JSONResponse.error(ex.getMessage());
		}
	}
	
	/**
	 * 通过ID获取订单
	 */
	@RequestMapping("/get")
	@ResponseBody
	public JSONResponse getOrderInfo(@RequestBody OrderModel order) {
		OrderModel o = orderService.queryOne(order);
		if(o == null) {
			return JSONResponse.error("已选订单不存在！");
		}
		return JSONResponse.success().put("order", o);
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
		
		//订单已存在验证
		OrderModel o = orderService.queryOne(order);
		if(o != null) {
			String errMsg = "订单已存在,订单号为："+order.getNo();
			return JSONResponse.error(errMsg);
		}
		orderService.add(order);
		return JSONResponse.success("新增订单成功!");
	}
	
	/**
	 * 订单信息
	 */
	@RequestMapping("/detail")
	public String showOrderInfo(@RequestParam(name="id") int id,
			@RequestParam(name="showDetail", required=false)boolean showDetail,
			Model model) {
		OrderModel order = new OrderModel();
		order.setId(id);
		order = orderService.queryOne(order);
		model.addAttribute("order", order);
		
		List<OrderPnModel> list = null;
		if( order!=null ) {
			list = orderPnService.queryList(order);
		}
		model.addAttribute("orderPns", list);
		model.addAttribute("showDetail", showDetail);
		
        return "order/detail";
    }
}
