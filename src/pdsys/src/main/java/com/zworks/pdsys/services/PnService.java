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
	@Autowired
	private WareHousePnService wareHousePnService;
	@Autowired
	private WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	private WareHouseDeliveryPnService wareHouseDeliveryPnService;
	
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

	@Transactional
	public void updatePnCls(PnModel pn) {
		PnPnClsRelModel pnClsRel = null;//预想只更新一个本体

		//更新本体的名称和单位
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRelTmp : clsRels) {
			PnClsModel pnCls = pnClsRelTmp.getPnCls();
			pnClsService.update(pnCls);
			
			pnClsRel = pnClsRelTmp;
		}
		if(pnClsRel == null) {
			return;
		}
		
		PnModel pnDb = queryOne(pn);
		if(pnDb == null) {
			throw new PdsysException("不存在的品目，刷新后再试");
		}
		
		//修改本体使用量，因为涉及到入出库时原包材计算，所以已经使用过不能修改
		for(PnPnClsRelModel pnClsRelTmp : pnDb.getPnClsRels()) {
			if(pnClsRelTmp.getPnCls().getId() == pnClsRel.getPnCls().getId()) {
				if(pnClsRel.getNum() != pnClsRelTmp.getNum()) {
					checkUsed(pn, false);
					//修改配比
					pnMapper.updatePnCls(pn);
				}
			}
		}
	}
	
	private void checkUsed(PnModel pn, boolean isCheckWhPn) {
		if(isCheckWhPn) {
			wareHousePnService.checkUsedPn(pn);
		}
		wareHouseEntryPnService.checkUsedPn(pn);
		wareHouseDeliveryPnService.checkUsedPn(pn);
	}

	@Transactional
	public void deletePnCls(PnModel pn) {
		//检测是否使用中，因为涉及到入出库计算则不能随便删除
		checkUsed(pn, false);
		
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRel : clsRels) {
			PnClsModel pnCls = pnClsRel.getPnCls();
			
			pnClsService.checkUsed(pnCls);
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

	@Transactional
	public void addBOM(PnModel pn) {
		for(PnBOMRelModel bomRel : pn.getPnBOMRels()) {
			bomRel.getFilterCond().put("pnId", pn.getId());
			pnMapper.addBOM(bomRel);
		}
	}

	public void deleteBOM(PnModel pn) {
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
