package com.zworks.pdsys.controllers;

import java.util.ArrayList;
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
import com.zworks.pdsys.common.utils.ValidatorUtils;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.PurchaseBomModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	@Autowired
	OrderPnService orderPnService;
	
	@Autowired
	PurchaseService purchaseService;
	
	/**
	 * 采购管理
	 */
	@RequestMapping("/list")
    public String showBomList(OrderModel order,Model model) {
		
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
	
	/**
	 * 新增采购单
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONResponse savePurchase(@RequestBody PurchaseModel purchase) {
		//验证处理
		JSONResponse JR = ValidatorUtils.doValidate(purchase);
		if( JR!=null )
			return JR;
		purchaseService.save(purchase);
		return JSONResponse.success("新增采购单成功!").put("id", purchase.getId());
	}
	
	/**
	 * 采购单详细
	 */
	@RequestMapping("/saveDetail")
	@ResponseBody
	public String purchaseDetail(@RequestBody int[] bomIds,
									   @RequestParam(name="purchaseId", required=false)Integer purchaseId,
									   @RequestParam(name="orderNo", required=false)String orderNo,Model model)
	{

		List<PurchaseBomModel> purchaseBoms = new ArrayList<PurchaseBomModel>();
		model.addAttribute("purchaseBoms", purchaseBoms);
		
        return "order/purchase/detail";
	}
	

}
