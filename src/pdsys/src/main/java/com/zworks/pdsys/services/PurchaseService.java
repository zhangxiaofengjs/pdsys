package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.annotations.SetApprovalInfo;
import com.zworks.pdsys.common.enumClass.ApprovalState;
import com.zworks.pdsys.common.enumClass.ApprovalType;
import com.zworks.pdsys.common.enumClass.NoticeType;
import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.mappers.PurchaseBOMMapper;
import com.zworks.pdsys.mappers.PurchaseMapper;
import com.zworks.pdsys.models.ApprovalInfoModel;
import com.zworks.pdsys.models.ApprovalNodeModel;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.models.UserModel;

@Service
public class PurchaseService {
	@Autowired
    private PurchaseMapper purchaseMapper;
	@Autowired
	private PurchaseBOMMapper purchaseBOMMapper;
	@Autowired
	PurchaseBOMService purchaseBOMService;
	@Autowired
	ApprovalService approvalService;
	@Autowired
	ApprovalInfoService approvalInfoService;
	@Autowired
    private NoticeService noticeService;
	
	@Transactional
	public void add(PurchaseModel purchase) {
		//TODO 现在默认设定为仅有的一条批复节点，以后其他需要承认需要界面设定
		ApprovalNodeModel node = approvalService.queryList(null).get(0);
		
		ApprovalInfoModel info = new ApprovalInfoModel();
		info.setNode(node);
		info.setState(ApprovalState.WORKING.ordinal());
		info.setType(ApprovalType.PURCHASE.ordinal());
		approvalInfoService.add(info);

		purchase.setApprovalInfo(info);
		purchaseMapper.add( purchase );
		purchaseBOMService.add(purchase.getPurchaseBOMs());
	}
	
	@Transactional
	public void delete(List<PurchaseModel> purchases){
		for(PurchaseModel purchase : purchases) {
			//先删除对应购物单BOM
			for(PurchaseBOMModel phBOM : purchase.getPurchaseBOMs()) {
				purchaseBOMMapper.delete(phBOM);
			}

			purchaseMapper.delete(purchase);
		}
	}

	@SetApprovalInfo
	public PurchaseModel queryOne(PurchaseModel purchase) {
		List<PurchaseModel> ps = queryList(purchase);
		if(ps.size() == 1) {
			return ps.get(0);
		}
		return null;
	}
	
	public void update(PurchaseModel purchase) {
		purchaseMapper.update(purchase);
	}

	@SetApprovalInfo
	public List<PurchaseModel> queryList(PurchaseModel purchase) {
		List<PurchaseModel> purchases = purchaseMapper.queryList(purchase);
		return purchases;
	}
	
	public boolean checkSupplierIdIsNull(PurchaseModel purchase)
	{
		List<PurchaseBOMModel> purchaseBoms = purchase.getPurchaseBOMs();
		for(PurchaseBOMModel pb : purchaseBoms) {
			if( pb.getSupplier() == null ) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public void requestApproval(PurchaseModel p) {
		//做审批
		ApprovalInfoModel approvalInfo = p.getApprovalInfo();
		ApprovalNodeModel node = approvalInfo.getNode();
		approvalInfoService.requestApproval(approvalInfo);
		
		//发送通知
		NoticeModel n = new NoticeModel();
		n.setContent(String.format("采购单等待审批,单号:<b style='color:#337ab7'>%s</b>", p.getNo()));
		n.setRefId(p.getId());
		n.setSender(SecurityContextUtils.getLoginUser().getUser());
		n.setType(NoticeType.APPROVAL.ordinal());
		for(UserModel u : node.getApprovalUsers()) {
			n.setReceiver(u);
			noticeService.add(n);
		}
	}

	public boolean responseApproval(PurchaseModel p, boolean ok) {
		//做审批
		ApprovalInfoModel approvalInfo = p.getApprovalInfo();
		ApprovalNodeModel node = approvalInfo.getNode();
		boolean allApprovaled = approvalInfoService.responseApproval(approvalInfo, ok);
		if(allApprovaled) {
			//无需进一步承认则设置为已经下单
			p.setState(PurchaseState.ORDERED.ordinal());
			p.setPurchaseDate(DateUtils.getCurrentDate());
			this.update(p);
		}
		
		UserModel user = SecurityContextUtils.getLoginUser().getUser();
		//发送通知
		//TODO needNextApproval如果要继续批复的话，还要发送等待审批通知
		NoticeModel n = new NoticeModel();
		n.setContent(String.format("采购单审批<b style='color:%s'>%s</b>, 单号:<b style='color:#337ab7'>%s</b>", 
				ok?"#085408":"#ff0000", 
				ok?"通过":"驳回",
				p.getNo()));

		n.setRefId(p.getId());
		n.setSender(user);
		n.setType(NoticeType.APPROVAL.ordinal());
		n.setReceiver(p.getUser());
		noticeService.add(n);

		for(UserModel u : node.getApprovalUsers()) {
			if(u.getId() != user.getId()) {
				//给除了自己以外可以承认审批的人发送消息
				n.setReceiver(u);
				noticeService.add(n);
			}
		}
		return false;
	}
}
