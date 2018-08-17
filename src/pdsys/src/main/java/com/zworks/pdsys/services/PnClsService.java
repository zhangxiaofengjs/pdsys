package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.mappers.PnClsMapper;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;

@Service
public class PnClsService {
	@Autowired
    private PnClsMapper pnClsMapper;
	@Autowired
	private WareHouseSemiPnService wareHouseSemiPnService;
	@Autowired
	private WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	private WareHouseDeliverySemiPnService wareHouseDeliverySemiPnService;
	
	public List<PnClsModel> queryList(PnClsModel pnClsModel) {
		return pnClsMapper.queryList(pnClsModel);
	}
	
	public PnClsModel queryOne(PnClsModel pnClsModel) {
		List<PnClsModel> list = pnClsMapper.queryList(pnClsModel);
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public void add(PnClsModel pnCls) {
		pnClsMapper.add(pnCls);
	}

	public void update(PnClsModel pnCls) {
		pnClsMapper.update(pnCls);
	}
	
	public boolean exists(PnClsModel pnCls) {
		List<PnClsModel> list = queryList(pnCls);
		return list.size() != 0;
	}

	public void delete(PnClsModel pnCls) {
		PnClsModel pnClsTmp = queryOne(pnCls);
		//删除关联的BOMRel
		if(!ListUtils.isNullOrEmpty(pnClsTmp.getPnClsBOMRels())) {
			pnClsMapper.deleteBOM(pnClsTmp);
		}
		pnClsMapper.delete(pnClsTmp);
	}
	
	public void addBOM(PnClsModel pnCls) {
		List<PnClsBOMRelModel> pnClsBOMRels = pnCls.getPnClsBOMRels();
		if(pnClsBOMRels == null) {
			return;
		}
		for(PnClsBOMRelModel bomRel : pnClsBOMRels) {
			bomRel.getFilterCond().put("pnClsId", pnCls.getId());
			pnClsMapper.addBOM(bomRel);
		}
	}

	public void deleteBOM(PnClsModel pnCls) {
		pnClsMapper.deleteBOM(pnCls);
	}
	
	public boolean existsBOM(PnClsModel pnCls) {
		PnClsModel targetPnCls = queryOne(pnCls);
		if(targetPnCls == null) {
			return false;
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
		
		return false;
	}

	public void checkUsed(PnClsModel pnCls) {
		wareHouseSemiPnService.checkUsedPnCls(pnCls);
		wareHouseEntrySemiPnService.checkUsedPnCls(pnCls);
		wareHouseDeliverySemiPnService.checkUsedPnCls(pnCls);
	}
}
