package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.mappers.PnMapper;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.BOMRelationModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;

@Service
public class PnService {
	@Autowired
    private PnMapper pnMapper;
	
	public List<PnModel> queryList(PnModel pn) {
		return pnMapper.queryList(pn);
	}
	
	public List<PnClsModel> queryClsList(PnModel pn) {
		return pnMapper.queryClsList( pn );
	}

	//TODO 为什么有关订单的东西都在PnService??
	public void save(OrderPnModel orderPn) {
		pnMapper.save( orderPn );
	}
	
	public void delete(OrderPnModel orderPn) {
		pnMapper.delete(orderPn);
	}
	
	public List<BomDetailModel> queryBomList(OrderModel order) {
		return pnMapper.queryBomList( order );
	}
	
	public List<OrderPnModel> queryPnByOrderPnId( OrderPnModel orderPn ){
		return pnMapper.queryPnByOrderPnId( orderPn );
	}
	
	public List<PnClsModel> queryClsByOrderPnId( OrderPnModel orderPn ){
		return pnMapper.queryClsByOrderPnId( orderPn );
	}

	public void add(PnModel pn) {
		pnMapper.add(pn);
	}

	public void update(PnModel pn) {
		pnMapper.update(pn);
	}

	public PnModel queryOne(PnModel pn) {
		List<PnModel> pns = queryList(pn);
		if(pns.size()==1) {
			return pns.get(0);
		}
		return null;
	}

	@Transactional
	public void addPnCls(PnModel pn) {
		pnMapper.addPnCls(pn);
	}

	public boolean existsPnCls(PnModel pn) {
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = queryOne(pn);
		
		if(p == null) {
			throw new PdsysException("品番的ID或者品番未指定！", PdsysExceptionCode.ERROR_PARAM);
		}

		List<PnClsModel> clss = pn.getPnClss();
		List<PnClsModel> targetClss = p.getPnClss();
		for(PnClsModel pnCls : clss) {
			for(PnClsModel targetPnCls : targetClss) {
				if(pnCls.getId() == targetPnCls.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean existsBOM(PnModel pn) {
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = queryOne(pn);
		
		if(p == null) {
			throw new PdsysException("品番的ID或者品番未指定！", PdsysExceptionCode.ERROR_PARAM);
		}

		List<BOMRelationModel> bomRels = pn.getBomRels();
		List<BOMRelationModel> targetBomRels = p.getBomRels();
		for(BOMRelationModel bomRel : bomRels) {
			BOMModel bom = bomRel.getBom();
			
			for(BOMRelationModel targetBomRel : targetBomRels) {
				BOMModel targetBom = targetBomRel.getBom();
				if(bom.getId() == targetBom.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public void addBOM(PnModel pn) {
		pnMapper.addBOM(pn);
	}

	public void updateBOM(PnModel pn) {
		pnMapper.updateBOM(pn);
	}
}
