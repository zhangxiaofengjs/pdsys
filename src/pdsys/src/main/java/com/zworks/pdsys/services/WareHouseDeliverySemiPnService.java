package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.mappers.WareHouseDeliverySemiPnMapper;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.WareHouseDeliverySemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Service
public class WareHouseDeliverySemiPnService {
	@Autowired
    private WareHouseDeliverySemiPnMapper wareHouseDeliverySemiPnMapper;
	
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	public List<WareHouseDeliverySemiPnModel> queryList(WareHouseDeliverySemiPnModel obj) {
		return wareHouseDeliverySemiPnMapper.queryList(obj);
	}

	public WareHouseDeliverySemiPnModel queryOne(WareHouseDeliverySemiPnModel deliverySemiPn) {
		List<WareHouseDeliverySemiPnModel> queryList = queryList(deliverySemiPn);
		if(ListUtils.isNullOrEmpty(queryList)) {
			return null;
		} else if(queryList.size() == 1) {
			return queryList.get(0);
		}
		
		throw new PdsysException("未想定数目：WareHouseDeliverySemiPnService-queryOne");
	}
	
	public void add(WareHouseDeliverySemiPnModel deliveryPn) {
		wareHouseDeliverySemiPnMapper.add(deliveryPn);
	}

	@Transactional
	public void delete(List<WareHouseDeliverySemiPnModel> deliveryPns) {
		for(WareHouseDeliverySemiPnModel deliveryPn : deliveryPns) {
			delete(deliveryPn);
		}
	}

	public void delete(WareHouseDeliverySemiPnModel deliveryPn) {
		wareHouseDeliverySemiPnMapper.delete(deliveryPn);
	}

	public boolean exists(WareHouseDeliverySemiPnModel deliveryPn) {
		List<WareHouseDeliverySemiPnModel> list = queryList(deliveryPn);
		return list.size() != 0;
	}

	public void update(WareHouseDeliverySemiPnModel deliveryPn) {
		wareHouseDeliverySemiPnMapper.update(deliveryPn);
	}

	public void checkUsedPnCls(PnClsModel pnCls) {
		WareHouseDeliverySemiPnModel semiPn = new WareHouseDeliverySemiPnModel();
		PnPnClsRelModel pnClsRel = new PnPnClsRelModel();
		pnClsRel.setPnCls(pnCls);
		semiPn.setPnClsRel(pnClsRel);
	
		List<WareHouseDeliverySemiPnModel> list = queryList(semiPn);
		if(!ListUtils.isNullOrEmpty(list)) {
			throw new PdsysException("出库单使用中");
		}
	}
}
