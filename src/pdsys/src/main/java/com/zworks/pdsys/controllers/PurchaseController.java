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
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
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
	@RequestMapping("/bomdetails")
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
		
        return "/purchase/bomdetails";
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
		purchaseService.savePurchase(purchase);
		return JSONResponse.success("新增采购单成功!").put("id", purchase.getId());
	}
	
	/**
	 * 采购单详细
	 */
	@RequestMapping("/saveDetail")
	public String purchaseDetail(@RequestParam(name="bomIds", required=false) int[] bomIds,
							     @RequestParam(name="purchaseId", required=false)Integer purchaseId,
							     @RequestParam(name="orderNo", required=false)String orderNo,Model model)
	{
		List<PurchaseBOMModel> purchaseBoms = new ArrayList<PurchaseBOMModel>();
		OrderModel order = new OrderModel();
		List<BOMDetailModel> list = orderPnService.queryBomList(order);
		for(int i =0;i<list.size();i++)
		{
			BOMDetailModel BOMDetail = list.get(i);
			for(int j =0;j<bomIds.length;j++)
			{
				int bomId = bomIds[j];
				if(BOMDetail.getBomId()==bomId)
				{
					PurchaseBOMModel purchaseBom = new PurchaseBOMModel();
					PurchaseModel purchase = new PurchaseModel();
					purchase.setId(purchaseId);
					//采购单号
					purchaseBom.setPurchase(purchase);

					BOMModel bom = new BOMModel();
					bom.setId(bomId);
					//原包材
					purchaseBom.setBom(bom);
					//数量
					purchaseBom.setNum(BOMDetail.getBomNum());
					//单价
					purchaseBom.setPrice(BOMDetail.getBomPrice());
					
					//追加一条采购单详细的记录
					purchaseBoms.add(purchaseBom);
				}
			}
		}
		
		if( purchaseBoms.size() > 0 )
			purchaseService.savePurchaseDetail(purchaseBoms);
		
		model.addAttribute("purchaseBoms", purchaseBoms);
		
        return "order/purchase/detail";
	}

}
