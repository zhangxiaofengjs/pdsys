package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.beans.BOMDetailModel;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
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
	 * 订单详细
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JSONResponse showOrderDetail(@RequestBody OrderModel order) {
		List<OrderPnModel> list = orderPnService.queryOrderPnList(order);
		return JSONResponse.success().put("orderDetail", list);
    }
	
	/**
	 * 添加品目订单详细
	 * */
	@RequestMapping(value="/add")
	@ResponseBody
    public JSONResponse saveOrderDetail(@RequestBody OrderPnModel orderPn) {
		boolean isExist = orderPnService.existsOrderPn(orderPn);
		if( isExist )
		{
			return JSONResponse.error("该品目已经存在！");
		}

		orderPnService.save(orderPn);
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
	
	@RequestMapping("/plan")
    public String plan(Model model) {
		
        return "order/plan";
    }
	
	/**
	 * 通过订单详细的ID取得品目
	 */
	@RequestMapping("/showPnInfo")
	@ResponseBody
	public JSONResponse queryPnByOrderPnId( OrderPnModel orderPn ){
		List<OrderPnModel> ops = orderPnService.queryPnByOrderPnId( orderPn );
		
		return JSONResponse.success().put("data", ops);
	}
	
	/**
	 * 通过订单详细的ID取得子品目
	 */
	@RequestMapping("/showClsInfo")
	@ResponseBody
	public JSONResponse queryClsByOrderPnId( OrderPnModel orderPn ){
		List<PnClsModel> clss = orderPnService.queryClsByOrderPnId( orderPn );
		return JSONResponse.success().put("data", clss);
	}
	
	/**
	 * 更新订单详细的ID取得品目
	 */
	@RequestMapping("/update")
	@ResponseBody
	public JSONResponse updateOrderPnInfo(@RequestBody OrderPnModel orderPn) {
		orderPnService.updateOrderPn(orderPn);
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
			List<BOMDetailModel> list = orderPnService.queryBomList(order);
			model.addAttribute("boms", list);
			model.addAttribute("order", order);
		}
		
        return "order/bomDetail";
    }

}
