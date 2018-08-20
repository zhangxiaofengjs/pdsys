package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.mappers.OrderPnMapper;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnBOMRelModel;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.models.WareHouseBOMModel;

@Service
public class OrderPnService {
	@Autowired
    private OrderPnMapper orderPnMapper;
	@Autowired
	private PurchaseBOMService purchaseBOMService;
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	
	public void add(OrderPnModel orderPn) {
		orderPnMapper.add( orderPn );
	}
	
	public void delete(OrderPnModel orderPn) {
		orderPnMapper.delete(orderPn);
	}

	public List<BOMUseNumBean> queryBOMUseNumList(OrderModel order) {
		OrderPnModel orderPnS = new OrderPnModel();
		orderPnS.setOrder(order);
		List<OrderPnModel> orderPns = queryList(orderPnS);
		Integer[] bomIds = (Integer[])order.getFilterCond().get("bomIds");
		List<Integer> includeBomIds = bomIds==null?null:Arrays.asList(bomIds);
		
		Map<Integer, OrderPnModel> pn2OrderPnMap = new HashMap<Integer, OrderPnModel>();
		//按照订购PN进行合计数量
		for(OrderPnModel orderPn : orderPns) {
			PnModel pn = orderPn.getPn();

			OrderPnModel oPn = pn2OrderPnMap.get(pn.getId());
			if(oPn == null) {
				pn2OrderPnMap.put(pn.getId(), orderPn);
			} else {
				oPn.setNum(oPn.getNum() + orderPn.getNum());
				oPn.setDeliveredNum(oPn.getDeliveredNum() + orderPn.getDeliveredNum());
			}
		}
		
		Map<Integer, BOMUseNumBean> bomMap = new HashMap<Integer, BOMUseNumBean>();
		//按照BOM进行合计数量
		for(Integer key : pn2OrderPnMap.keySet()) {
			OrderPnModel orderPn = pn2OrderPnMap.get(key);
			PnModel pn = orderPn.getPn();
			float num = orderPn.getNum();
			float deliveredNum = orderPn.getDeliveredNum();
			float producedNum = orderPn.getWhpn() == null ? 0 : orderPn.getWhpn().getProducedNum();
			float needNum = num - deliveredNum - producedNum;

			if(needNum < 0) {
				//已经生产的够多了，该BOM充足
				continue;
			}
			
			//合计通才
			List<PnBOMRelModel> pnBOMRels = pn.getPnBOMRels();
			for(PnBOMRelModel pnBomRel : pnBOMRels) {
				BOMModel bom = pnBomRel.getBom();
				
				if(ListUtils.isNullOrEmpty(includeBomIds) || includeBomIds.contains(bom.getId()))
					calcUseNum(bomMap, needNum, pnBomRel.getUseNum(), bom);
			}
			
			//合计各个子类用材
			List<PnPnClsRelModel> pnClsRels = pn.getPnClsRels();
			for(PnPnClsRelModel pnClsRel : pnClsRels) {
				PnClsModel pnCls = pnClsRel.getPnCls();
				
				List<PnClsBOMRelModel> bomRels = pnCls.getPnClsBOMRels();
				for(PnClsBOMRelModel pnBomRel : bomRels) {
					BOMModel bom = pnBomRel.getBom();
					
					if(ListUtils.isNullOrEmpty(includeBomIds) || includeBomIds.contains(bom.getId()))
						calcUseNum(bomMap, needNum, pnBomRel.getUseNum(), bom);
				}
			}
		}
		
		List<BOMUseNumBean> list = new ArrayList<BOMUseNumBean>(bomMap.values());
		Collections.sort(list, new Comparator<BOMUseNumBean>() {  
            @Override  
            public int compare(BOMUseNumBean s1, BOMUseNumBean s2) {
            	BOMModel bom1 = s1.getBom();
            	BOMModel bom2 = s2.getBom();
            	
            	if(bom1.getType()!=bom2.getType()){  
			       return bom2.getType()-(bom1.getType());  
			    } else {  
			       return bom1.getPn().compareTo(bom2.getPn());  
			    }
            }
        });
		return list;
	}

	private void calcUseNum(Map<Integer, BOMUseNumBean> bomMap, float needNum, float useNum, BOMModel bom) {
		PurchaseModel p = new PurchaseModel();
		p.setState(PurchaseState.ORDERED.ordinal());
		PurchaseBOMModel pb = new PurchaseBOMModel();
		pb.setPurchase(p);
		pb.getFilterCond().put("groupByBOM", true);
		
		WareHouseBOMModel whBom = new WareHouseBOMModel();
		whBom.setBom(bom);
		
		BOMUseNumBean bomUseBean = bomMap.get(bom.getId());
		if(bomUseBean == null) {
			bomUseBean = new BOMUseNumBean();
			bomUseBean.setBom(bom);
			bomUseBean.setUseNum(needNum * useNum);
			
			whBom.setBom(bom);
			WareHouseBOMModel whBomTmp = wareHouseBOMService.queryOne(whBom);
			if(whBomTmp != null) {
				bomUseBean.setWareHouseNum(whBomTmp.getNum());
			}
			
			pb.setBom(bom);
			PurchaseBOMModel purchaseBOM = purchaseBOMService.queryOne(pb);
			if(purchaseBOM != null) {
				bomUseBean.setPurchasedNum(purchaseBOM.getNum());
			}
			
			bomMap.put(bom.getId(), bomUseBean);
		} else {
			bomUseBean.setUseNum(bomUseBean.getUseNum() + needNum * useNum);
		}
	}
	
	public OrderPnModel queryOne( OrderPnModel orderPn ){
		List<OrderPnModel> ops = orderPnMapper.queryOrderPns(orderPn);
		if(ops.size() == 1) {
			return ops.get(0);
		}
		return null;
	}
	
	public List<OrderPnModel> queryList( OrderPnModel orderPn ) {
		return orderPnMapper.queryList( orderPn );
	}
	
	public void update(OrderPnModel orderPnModel) {
		orderPnMapper.update( orderPnModel );
	}
	
	public boolean existsOrderPn(OrderPnModel orderPn) {
		List<OrderPnModel> ops = orderPnMapper.queryOrderPns(orderPn);
		if(ops.size()>0) {
			return true;
		}
		return false;
	}

}
