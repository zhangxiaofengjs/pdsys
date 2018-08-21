package com.zworks.pdsys.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.common.enumClass.BOMType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.ListUtils;
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
	
	public PnModel queryByClsId(int pnClsId) {
		PnModel pn = new PnModel();
		pn.putFilterCond(PnModel.FCK_PNCLSID, pnClsId);
		return queryOne(pn);
	}
	
	public void add(PnModel pn) {
		pnMapper.add(pn);
	}

	public void update(PnModel pn) {
		pnMapper.update(pn);
	}

	public void delete(PnModel pn) {
		pnMapper.delete(pn);
	}
	
	public boolean exists(PnModel pn) {
		List<PnModel> pns = queryList(pn);
		return pns.size()!=0;
	}
	
	public PnModel queryOne(PnModel pn) {
		List<PnModel> pns = queryList(pn);
		if(pns.size()==1) {
			return pns.get(0);
		}
		throw new PdsysException("超出预想数目:PN-queryOne");
	}

	@Transactional
	public void addPnCls(PnModel pn) {
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRel : clsRels) {
			PnClsModel pnCls = pnClsRel.getPnCls();
			pnClsService.add(pnCls);
			pnClsService.addBOM(pnCls);
			
			pnClsRel.getFilterCond().put("pnId", pn.getId());
			pnMapper.addPnCls(pnClsRel);
		}
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

	public void updatePnCls(PnModel pn) {
		pnMapper.updatePnCls(pn);
	}
	
	@Transactional
	public void deletePnCls(PnModel pn) {
		if(ListUtils.isNullOrEmpty(pn.getPnClsRels())) {
			return;
		}
		pnMapper.deletePnCls(pn);
	}

	@Transactional
	public void addBOM(PnModel pn) {
		for(PnBOMRelModel bomRel : pn.getPnBOMRels()) {
			bomRel.getFilterCond().put("pnId", pn.getId());
			pnMapper.addBOM(bomRel);
		}
	}

	public void deleteBOM(PnModel pn) {
		if(ListUtils.isNullOrEmpty(pn.getPnBOMRels())) {
			return;
		}
		pnMapper.deleteBOM(pn);
	}
	
	public Map<Integer, BOMUseNumBean> calcUsedBOM(PnModel p, PnPnClsRelModel pnClsRel, boolean isSemi, float count) {
		PnModel pn = queryOne(p);
		
		Map<Integer, BOMUseNumBean> bomMap = new HashMap<Integer, BOMUseNumBean>();
		
		//特有材计算
		PnPnClsRelModel pnClsRelTgt = null;
		List<PnPnClsRelModel> pnClsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRelTmp : pnClsRels) {
			PnClsModel pnClsTmp = pnClsRelTmp.getPnCls();

			//指定子类时，只计算指定子类的半成品
			if(pnClsRel != null) {
				PnClsModel pnCls = pnClsRel.getPnCls();
				if(pnCls.getId() != pnClsTmp.getId()) {
					continue;
				}
			}
			
			List<PnClsBOMRelModel> pnClsBOMRels = pnClsTmp.getPnClsBOMRels();
			for(PnClsBOMRelModel pnClsBOMRel : pnClsBOMRels) {
				BOMModel bom = pnClsBOMRel.getBom();
				if(!isSemi && bom.getType() == BOMType.Packing.ordinal()) {//成品只计算包材损耗，因为在半成品入库时已经减掉原材
					float useNum = pnClsBOMRel.getUseNum() * count;
					addUsedBOM(bomMap, bom, useNum);
				}
				else if(isSemi && bom.getType() == BOMType.RAW.ordinal()) { //半成品只计算原材损耗
					float useNum = pnClsBOMRel.getUseNum() / pnClsRelTmp.getNum() * count;
					addUsedBOM(bomMap, bom, useNum);
				}
			}
			
			pnClsRelTgt = pnClsRelTmp;
		}
		
		//每箱中包含子类的个数
		float countByBox = pnClsRelTgt.getNum();
		
		//共通材计算
		List<PnBOMRelModel> pnBOMRels = pn.getPnBOMRels();
		for(PnBOMRelModel pnBOMRel : pnBOMRels) {
			BOMModel bom = pnBOMRel.getBom();
			
			if(!isSemi && bom.getType() == BOMType.Packing.ordinal()) {//成品只计算包材损耗，因为在半成品入库时已经减掉原材
				float useNum = pnBOMRel.getUseNum() * count;
				addUsedBOM(bomMap, bom, useNum);
			}
			else if(isSemi && bom.getType() == BOMType.RAW.ordinal()) { //半成品只计算原材损耗
				float useNum = pnBOMRel.getUseNum() / countByBox * count;
				addUsedBOM(bomMap, bom, useNum);
			}
		}
		
		return bomMap;
	}

	private void addUsedBOM(Map<Integer, BOMUseNumBean> bomMap, BOMModel bom, float useNum) {
		BOMUseNumBean bean = bomMap.get(bom.getId());
		if(bean == null)
		{
			bean = new BOMUseNumBean();
			bean.setBom(bom);
			bomMap.put(bom.getId(), bean);
		}
		bean.setUseNum(bean.getUseNum() + useNum);
	}
}
