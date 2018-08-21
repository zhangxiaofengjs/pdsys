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
}
