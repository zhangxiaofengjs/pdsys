package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zworks.pdsys.business.beans.BOMDetailModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.services.OrderPnService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	@Autowired
	OrderPnService orderPnService;
	
	/**
	 * 采购管理
	 */
	@RequestMapping("/list")
    public String purchase(OrderModel order,Model model) {
		
		boolean isExist = true;
		List<BOMDetailModel> list = orderPnService.queryBomList(order);
		for(int i =0;i<list.size();i++)
		{
			BOMDetailModel bom = list.get(i);
			if( bom ==null)
			{
				isExist = false;
				break;
			}
		}
		
		if( isExist )
			model.addAttribute("boms", list);
		
		model.addAttribute("order", order);
		
        return "order/purchase/main";
    }

}
