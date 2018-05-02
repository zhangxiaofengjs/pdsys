package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.mappers.PnClsMapper;
import com.zworks.pdsys.mappers.PnMapper;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;

@Service
public class PnService {
	@Autowired
    private PnMapper pnMapper;
	@Autowired
	private PnClsMapper pnClsMapper;
	
	public List<PnModel> queryList(PnModel pn) {
		return pnMapper.queryList(pn);
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
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRel : clsRels) {
			PnClsModel pnCls = pnClsRel.getPnCls();
			pnClsMapper.add(pnCls);
		}
		
		pnMapper.addPnCls(pn);
	}

	public boolean existsPnCls(PnModel pn) {
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = queryOne(pn);
		
		if(p == null) {
			throw new PdsysException("品番的ID或者品番未指定！", PdsysExceptionCode.ERROR_PARAM);
		}

		List<PnPnClsRelModel> clss = pn.getPnClsRels();
		List<PnPnClsRelModel> targetClss = p.getPnClsRels();
		for(PnPnClsRelModel pnClsRel : clss) {
			PnClsModel pnCls = pnClsRel.getPnCls();

			for(PnPnClsRelModel targetPnClsRel : targetClss) {
				PnClsModel pnClsTarget = targetPnClsRel.getPnCls();
				if(pnCls.getName().equals(pnClsTarget.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional
	public void deletePnCls(PnModel pn) {
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRel : clsRels) {
			PnClsModel pnCls = pnClsRel.getPnCls();
			pnClsMapper.delete(pnCls);
		}
		pnMapper.deletePnCls(pn);
	}

	public boolean existsBOM(PnModel pn) {
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = queryOne(pn);
		
		if(p == null) {
			throw new PdsysException("品番的ID或者品番未指定！", PdsysExceptionCode.ERROR_PARAM);
		}

		List<PnPnClsRelModel> clsRels = p.getPnClsRels();
		List<PnPnClsRelModel> targetClsRels = pn.getPnClsRels();
		
		for(PnPnClsRelModel clsRel : clsRels) {
			PnClsModel pnCls = clsRel.getPnCls();
			
			//寻找同样的子类
			PnClsModel targetPnCls = null;
			for(PnPnClsRelModel pnClsRel : targetClsRels) {
				PnClsModel tmpPnCls = pnClsRel.getPnCls();
				if(pnCls.getId() ==  tmpPnCls.getId()) {
					targetPnCls = tmpPnCls;
					break;
				}
			}
			
			if(targetPnCls == null) {
				continue;//没有同样的子类，那么肯定没有一样的BOM
			}
			
			List<PnClsBOMRelModel> bomRels = pnCls.getPnClsBOMRels();
			List<PnClsBOMRelModel> targetBomRels = targetPnCls.getPnClsBOMRels();
			
			for(PnClsBOMRelModel bomRel : bomRels) {
				for(PnClsBOMRelModel bRel : targetBomRels) {
					if(bomRel.getBom().getId() == bRel.getBom().getId()) {
						//同样子类下有同样的BOM
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public void addBOM(PnModel pn) {
		pnMapper.addBOM(pn);
	}

	public void deleteBOM(PnModel pn) {
		pnMapper.deleteBOM(pn);
	}
}
