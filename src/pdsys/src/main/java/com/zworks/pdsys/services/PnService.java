package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.mappers.PnClsMapper;
import com.zworks.pdsys.mappers.PnMapper;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnBOMRelModel;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;

@Service
public class PnService {
	@Autowired
    private PnMapper pnMapper;
	@Autowired
	private PnClsService pnClsService;
	
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
			pnClsService.add(pnCls);
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
			pnClsService.delete(pnCls);
		}
		pnMapper.deletePnCls(pn);
	}

	public boolean existsBOM(PnModel pn) {
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = queryOne(pn);
		
		if(p == null) {
			return false;
		}

		List<PnBOMRelModel> bomRels = p.getPnBOMRels();
		if(bomRels == null) {
			return false;
		}
		List<PnBOMRelModel> targetBomRels = pn.getPnBOMRels();
		if(targetBomRels == null) {
			return false;
		}
		
		for(PnBOMRelModel bomRel : bomRels) {
			BOMModel bom = bomRel.getBom();
			
			//寻找同样的
			for(PnBOMRelModel tgtbomRel : targetBomRels) {
				BOMModel tmpbom = tgtbomRel.getBom();
				if(bom.getId() ==  tmpbom.getId()) {
					return true;
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
