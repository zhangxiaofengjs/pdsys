package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.beans.SemiPnClsDetailBean;
import com.zworks.pdsys.business.beans.SemiPnDetailBean;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.mappers.WareHouseSemiPnMapper;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Service
public class WareHouseSemiPnService {
	@Autowired
    private WareHouseSemiPnMapper wareHouseSemiPnMapper;
	@Autowired
	private PnService pnService;
	
	public List<WareHouseSemiPnModel> queryList(WareHouseSemiPnModel filterObj) {
		List<WareHouseSemiPnModel> list = wareHouseSemiPnMapper.queryList(filterObj);
		
		return list;
	}
	

	public List<WareHouseSemiPnModel> queryListByPn(String strPn, boolean fuzzyPnSearch) {
		PnModel pn = new PnModel();
		pn.setPn(strPn);

		WareHouseSemiPnModel whPn = new WareHouseSemiPnModel();
		whPn.setPn(pn);
		
		if(fuzzyPnSearch) {
			whPn.putFilterCond("fuzzyPnSearch", true);
		}
		return queryList(whPn);
	}
	
	public WareHouseSemiPnModel queryOne(WareHouseSemiPnModel filterObj) {
		List<WareHouseSemiPnModel> list = queryList(filterObj);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public void update(WareHouseSemiPnModel wareHousePn) {
		wareHouseSemiPnMapper.update(wareHousePn);
	}

	public void delete(WareHouseSemiPnModel wareHousePn) {
		wareHouseSemiPnMapper.delete(wareHousePn);
	}

	public void add(WareHouseSemiPnModel wareHousePn) {
		wareHouseSemiPnMapper.add(wareHousePn);
	}

	public List<SemiPnDetailBean> convertToSemiPnList(List<WareHouseSemiPnModel> list) {
		List<SemiPnDetailBean> l = new ArrayList<SemiPnDetailBean>();
		
		for(WareHouseSemiPnModel whpn : list) {
			PnModel pn = whpn.getPn();
			PnPnClsRelModel pnClsRel = whpn.getPnClsRel();
			
			SemiPnDetailBean tgtB = null;
			for(SemiPnDetailBean b : l) {
				PnModel bpn = b.getPn();
				if(bpn.getId() == pn.getId()) {
					tgtB = b;
					break;
				}
			}
			
			if(tgtB == null) {
				tgtB = new SemiPnDetailBean();
				tgtB.setPn(pn);
				
				PnModel pnTmp = pnService.queryOne(pn);
				
				List<SemiPnClsDetailBean> arrayList = new ArrayList<SemiPnClsDetailBean>();
				for(PnPnClsRelModel pnClsRelTmp : pnTmp.getPnClsRels()) {
					SemiPnClsDetailBean clsB = new SemiPnClsDetailBean();
					clsB.setPnCls(pnClsRelTmp.getPnCls());
					clsB.setUnitNum(pnClsRelTmp.getNum());
					arrayList.add(clsB);
				}
				tgtB.setPnClsDetails(arrayList);
				l.add(tgtB);
			}
			
			for(SemiPnClsDetailBean db : tgtB.getPnClsDetails()) {
				if(db.getPnCls().getId() == pnClsRel.getPnCls().getId()) {
					db.setNum(whpn.getNum());
					db.setDefectiveNum(whpn.getDefectiveNum());
				}
			}
		}
		
		return l;
	}

	public void checkUsedPnCls(PnClsModel pnCls) {
		WareHouseSemiPnModel semiPn = new WareHouseSemiPnModel();
		PnPnClsRelModel pnClsRel = new PnPnClsRelModel();
		pnClsRel.setPnCls(pnCls);
		semiPn.setPnClsRel(pnClsRel);

		List<WareHouseSemiPnModel> list = queryList(semiPn);
		if(!ListUtils.isNullOrEmpty(list)) {
			semiPn = list.get(0);
			PnModel pn = semiPn.getPn();
			throw new PdsysException(String.format("半成品仓库使用中:<br>品目:%s %s <br>本体:%s", pn.getPn(), pn.getName(), semiPn.getPnClsRel().getPnCls().getName()));
		}
	}
}
