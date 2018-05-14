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
import com.zworks.pdsys.business.beans.PurchaseBOMListFromBean;
import com.zworks.pdsys.common.enumClass.OrderState;
import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.ValidatorUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.PurchaseBOMService;
import com.zworks.pdsys.services.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	@Autowired
	OrderPnService orderPnService;
	
	@Autowired
	PurchaseService purchaseService;
	
	@Autowired
	BOMService bomService;
	
	@Autowired
	PurchaseBOMService purchaseBOMService;
	/**
	 * 采购管理
	 */
	@RequestMapping("/bomdetails")
    public String showBomList(OrderModel order,Model model) {
		//只计算生产中
		order.setState(OrderState.PRODUCTING.ordinal());

		List<BOMDetailModel> list = orderPnService.queryBomList(order);
		model.addAttribute("boms", list);
		model.addAttribute("order", order);

        return "/purchase/bomdetails";
    }
	
	/**
	 * 新增采购单
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONResponse savePurchase(@RequestBody PurchaseModel purchase,
									 @RequestParam(name="bomIds", required=false) int[] bomIds,
									 @RequestParam(name="orderNo", required=false)String orderNo,Model model) {

		JSONResponse JR = ValidatorUtils.doValidate(purchase);
		if( JR!=null )
			return JR;
		
		purchaseService.savePurchase(purchase);
		int purchaseId = purchase.getId();
		
		//新增采购单详细
		List<PurchaseBOMModel> purchaseBoms = new ArrayList<PurchaseBOMModel>();
		OrderModel order = new OrderModel();
		order.setNo(orderNo);
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
					PurchaseModel p = new PurchaseModel();
					p.setId(purchaseId);
					//采购单号
					purchaseBom.setPurchase(p);

					BOMModel bom = bomService.queryById(bomId);
					if(bom == null) {
						bom = new BOMModel();
					}
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

		return JSONResponse.success("采购单追加成功！").put("purchaseId", purchaseId);

	}
	
	/**
	 * 采购单详细
	 */
	@RequestMapping("/purchasebomlist")
	public String purchaseBOMList(PurchaseBOMListFromBean formBean, Model model)
	{
		PurchaseModel purchase = new PurchaseModel();
		purchase.setId(formBean.getPurchaseId());
		
		if(formBean.getBomPn() != null) {
			List<PurchaseBOMModel> purchaseBOMs = new ArrayList<PurchaseBOMModel>();
			purchase.setPurchaseBOMs(purchaseBOMs);
			
			PurchaseBOMModel purchaseBOM = new PurchaseBOMModel();
			purchaseBOMs.add(purchaseBOM);
			
			BOMModel bom = new BOMModel();
			bom.setPn(formBean.getBomPn());
			purchaseBOM.setBom(bom);
		}
		
		PurchaseModel p = purchaseService.queryOne(purchase);
		if(p == null) {
			p = new PurchaseModel();
		}
		model.addAttribute("purchase", p);
		model.addAttribute("formBean", formBean);
		
        return "/purchase/purchasebomlist";
	}
	
	/**
	 * 删除采购单
	 * */
	@RequestMapping(value="/delete/purchase")
	@ResponseBody
    public JSONResponse deletePurchase(@RequestBody List<PurchaseModel> purchases, Model model) {
		List<PurchaseModel> delTarget = new ArrayList<PurchaseModel>();
		for(PurchaseModel ph : purchases) {
			PurchaseModel p = purchaseService.queryOne(ph);
			if(p == null) {
				continue;
			}
			if(p.getState() != PurchaseState.PLANNING.ordinal()) {
				return JSONResponse.error("只能删除计划中的采购单");
			}
			
			delTarget.add(p);
		}
		purchaseService.delete(delTarget);
		return JSONResponse.success("采购单删除成功！");
    }
	
	/**
	 * 删除采购单明细
	 * */
	@RequestMapping(value="/delete/bom")
	@ResponseBody
    public JSONResponse deletePurchaseDetail(@RequestBody List<PurchaseBOMModel> purchaseBoms, Model model) {
		
		purchaseBOMService.delPurchaseDetail(purchaseBoms);
		return JSONResponse.success("采购单明细删除成功！");
    }
	
	/**
	 * 下单
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public JSONResponse updatePurchaseState(@RequestBody PurchaseModel purchase) {
		PurchaseModel p = purchaseService.queryOne(purchase);
		if(p == null) {
			return JSONResponse.error("采购单不存在！");
		}

		if( purchaseService.checkSupplierIdIsNull(purchase) )
		{
			return JSONResponse.error("请先进行修改操作，选择一个供应商！");
		}
		p.setState(PurchaseState.ORDERED.ordinal());
		p.setPurchaseDate(DateUtils.getCurrentDate());
		purchaseService.updatePurchase(p);
		return JSONResponse.success("下单成功！");
	}
	
	/**
	 * 原包材
	 */
	@RequestMapping("/get")
	@ResponseBody
	public JSONResponse showPurchaseInfo(@RequestBody PurchaseBOMModel purchaseBom) {
		PurchaseBOMModel pb = purchaseService.queryPurchaseBOM(purchaseBom);
		if(pb == null) {
			return JSONResponse.error("不存在的采购单信息！");
		}

		return JSONResponse.success().put("pb", pb);
	}
	
	/**
	 * 修改采购单详细
	 */
	@RequestMapping("/updatePB")
	@ResponseBody
	public JSONResponse updatePB(@RequestBody PurchaseBOMModel purchaseBom) {
		purchaseService.updatePB(purchaseBom);
		return JSONResponse.success();
	}
	
	/**
	 * 采购单一览
	 */
	@RequestMapping("/list")
    public String showList(PurchaseModel purchase, Model model) {
		
		//采购单一览加载
		purchase.getFilterCond().put("fuzzyNoSearch", true);
		
		List<PurchaseModel> list = purchaseService.queryList(purchase);
		model.addAttribute("purchases", list);
		model.addAttribute("purchase", purchase);
		
        return "purchase/list";
    }

}
