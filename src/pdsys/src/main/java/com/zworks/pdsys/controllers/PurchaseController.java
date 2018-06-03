package com.zworks.pdsys.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.business.beans.PurchaseBOMListFromBean;
import com.zworks.pdsys.common.enumClass.ApprovalState;
import com.zworks.pdsys.common.enumClass.OrderState;
import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.ValidatorUtils;
import com.zworks.pdsys.models.ApprovalInfoModel;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.services.ApprovalInfoService;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.PurchaseBOMService;
import com.zworks.pdsys.services.PurchaseService;
import com.zworks.pdsys.services.WareHouseEntryService;

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

	@Autowired
	private WareHouseEntryService wareHouseEntryService;
	@Autowired
	private ApprovalInfoService approvalInfoService;
	/**
	 * 采购管理
	 */
	@RequestMapping("/bomdetails")
    public String showBomList(OrderModel order,Model model) {
		//只计算生产中
		order.setState(OrderState.PRODUCTING.ordinal());

		List<BOMUseNumBean> list = orderPnService.queryBOMUseNumList(order);
		model.addAttribute("list", list);
		model.addAttribute("order", order);

        return "purchase/bomdetails";
    }
	
	/**
	 * 新增采购单
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONResponse savePurchase(@RequestBody PurchaseModel purchase,
									 @RequestParam(name="bomIds", required=false) Integer[] bomIds,
									 @RequestParam(name="orderNo", required=false)String orderNo,Model model) {

		JSONResponse JR = ValidatorUtils.doValidate(purchase);
		if( JR!=null )
			return JR;
		
		//新增采购单详细
		List<PurchaseBOMModel> purchaseBoms = new ArrayList<PurchaseBOMModel>();
		OrderModel order = new OrderModel();
		order.setNo(orderNo);
		order.setState(OrderState.PRODUCTING.ordinal());
		order.getFilterCond().put("bomIds", bomIds);
		List<BOMUseNumBean> list = orderPnService.queryBOMUseNumList(order);
		for(int i =0;i<list.size();i++) {
			BOMUseNumBean BOMDetail = list.get(i);
			PurchaseBOMModel purchaseBom = new PurchaseBOMModel();
			//采购单号
			purchaseBom.setPurchase(purchase);
			//原包材
			purchaseBom.setBom(BOMDetail.getBom());
			//数量
			purchaseBom.setNum(BOMDetail.getUseNum());
			//单价
			purchaseBom.setPrice(BOMDetail.getBom().getPrice());
			
			//追加一条采购单详细的记录
			purchaseBoms.add(purchaseBom);
		}
		purchase.setPurchaseBOMs(purchaseBoms);
		purchaseService.add(purchase);
		
		return JSONResponse.success("采购单追加成功！").put("purchaseId", purchase.getId());
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
		
        return "purchase/purchasebomlist";
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
		
		purchaseBOMService.delete(purchaseBoms);
		return JSONResponse.success("采购单明细删除成功！");
    }
	
	/**
	 * 新增采购单明细
	 * */
	@RequestMapping(value="/addPB")
	@ResponseBody
    public JSONResponse addPB(@RequestBody PurchaseBOMModel purchaseBom) {
		//验证处理
		if( purchaseBom.getBom()!=null && purchaseBom.getBom().getId()<0 )
		{
			return JSONResponse.error("请选择原包材！");
		}
		
		JSONResponse JR = ValidatorUtils.doValidate(purchaseBom);
		if( JR!=null )
			return JR;
		
		purchaseBOMService.add(purchaseBom);
		return JSONResponse.success();
    }
	
	/**
	 * 原包材
	 */
	@RequestMapping("/get/purchasebom")
	@ResponseBody
	public JSONResponse showPurchaseInfo(@RequestBody PurchaseBOMModel purchaseBom) {
		PurchaseBOMModel pb = purchaseBOMService.queryOne(purchaseBom);
		if(pb == null) {
			return JSONResponse.error("不存在的采购单信息！");
		}

		return JSONResponse.success().put("pb", pb);
	}
	
	/**
	 * 当前采购以外的原包材取得
	 */
	@RequestMapping("/get/bom")
	@ResponseBody
	public JSONResponse showOtherBoms(@RequestBody PurchaseModel purchase) {
		PurchaseModel p = purchaseService.queryOne(purchase);
		if(p == null) {
			p = new PurchaseModel();
		}
		
		List<PurchaseBOMModel> pbs = p.getPurchaseBOMs();
		BOMModel bom = new BOMModel();
		List<BOMModel> boms = bomService.queryList(bom);
		if( pbs==null || pbs.size()==0)
			return JSONResponse.success().put("boms", boms);
		
		Iterator<BOMModel> bomsIterator = boms.iterator();
		while (bomsIterator.hasNext()) { 
			BOMModel b = bomsIterator.next();
			for(int i =0;i<pbs.size();i++)
			{
				PurchaseBOMModel pb = pbs.get(i);
				int bomId = pb.getBom().getId();
				if(b.getId()==bomId)
				{
					bomsIterator.remove();
					break;
				}
			}
		}

		return JSONResponse.success().put("boms", boms);
	}
	
	/**
	 * 当前原包材的供应商取得
	 */
	@RequestMapping("/get/supplier")
	@ResponseBody
	public JSONResponse showSuppliers(@RequestBody BOMModel bom) {
		
		List<SupplierModel> suppliers = new ArrayList<SupplierModel>();
		if(bom.getId()<1)
			return JSONResponse.success().put("suppliers", suppliers);

		List<BOMModel> boms = bomService.queryList(bom);
		if( boms==null || boms.size()==0)
			return JSONResponse.success().put("suppliers", suppliers);
		
		BOMModel b = boms.get(0);
		suppliers = b.getSuppliers();
		if( b.getSuppliers() == null )
			return JSONResponse.success().put("suppliers", suppliers);
		
		return JSONResponse.success().put("suppliers", b.getSuppliers());
	}
	
	/**
	 * 修改采购单详细
	 */
	@RequestMapping("/updatePB")
	@ResponseBody
	public JSONResponse updatePB(@RequestBody PurchaseBOMModel purchaseBom) {
		purchaseBOMService.update(purchaseBom);
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
	
	@RequestMapping("/entry")
	@ResponseBody
    public JSONResponse entry(@RequestBody PurchaseModel purchase, Model model) {
		PurchaseModel p = purchaseService.queryOne(purchase);
		if(p == null) {
			return JSONResponse.error("该采购单不存在，请刷新重试");
		}
		if(p.getState() != PurchaseState.ORDERED.ordinal()) {
			return JSONResponse.error("该采购单还未下单");
		}
		
		WareHouseEntryModel entry = purchase.getWareHouseEntry();
		JSONResponse res = wareHouseEntryService.checkAddable(entry);
		if(!res.isSuccess()) {
			return res;
		}
		
		p.setWareHouseEntry(entry);
		purchaseService.entry(p);
		return JSONResponse.success();
    }
	
	/**
	 * 提交审批
	 * @param purchase
	 * @param model
	 * @return
	 */
	@RequestMapping("/approval/request")
	@ResponseBody
    public JSONResponse approvalRequest(@RequestBody PurchaseModel purchase, Model model) {
		PurchaseModel p = purchaseService.queryOne(purchase);
		if(p == null) {
			return JSONResponse.error("该采购单不存在，请刷新重试");
		}
		if( purchaseService.checkSupplierIdIsNull(p) )
		{
			return JSONResponse.error("请先进行修改操作，选择一个供应商！");
		}
		if(p.getState() != PurchaseState.PLANNING.ordinal()) {
			return JSONResponse.error("该采购单不是在计划中状态");
		}
		ApprovalInfoModel approvalInfo = p.getApprovalInfo();
		if(approvalInfo.getState() != ApprovalState.WORKING.ordinal() &&
				approvalInfo.getState() != ApprovalState.NG.ordinal()) {
			return JSONResponse.error("该采购单已经提交过或者已经批复");
		}
		
		approvalInfoService.requestApproval(approvalInfo);
		return JSONResponse.success();
	}
	
	/**
	 * 审批后下单
	 */
	@RequestMapping("/approval/response/{result}")
	@ResponseBody
	public JSONResponse approvalResponse(@PathVariable(name="result" ,required=true)String result, @RequestBody PurchaseModel purchase) {
		PurchaseModel p = purchaseService.queryOne(purchase);
		if(p == null) {
			return JSONResponse.error("采购单不存在！");
		}
		
		if(p.getState() != PurchaseState.PLANNING.ordinal()) {
			return JSONResponse.error("该采购单不是在计划中状态");
		}
		ApprovalInfoModel approvalInfo = p.getApprovalInfo();
		if(!approvalInfoService.needApproval(approvalInfo)) {
			return JSONResponse.error("该采购单已经提交过或者已经批复/驳回");
		}
		if(!approvalInfoService.isLoginUserApprovalable(approvalInfo)) {
			return JSONResponse.error("您没有权限进行审批");
		}
		
		if(approvalInfoService.responseApproval(approvalInfo, "ok".equals(result))) {
			p.setState(PurchaseState.ORDERED.ordinal());
			p.setPurchaseDate(DateUtils.getCurrentDate());
			purchaseService.update(p);
			return JSONResponse.success("审批并且下单成功！");
		}
		return JSONResponse.success("操作成功！");
	}
}
