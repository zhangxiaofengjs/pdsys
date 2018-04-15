package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.mappers.PnClsMapper;
import com.zworks.pdsys.mappers.PnMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;

@Service
public class PnClsService {
	@Autowired
    private PnClsMapper pnClsMapper;
	
	public List<PnClsModel> queryList(PnClsModel pnClsModel) {
		return pnClsMapper.queryList(pnClsModel);
	}

	public void add(PnClsModel pnCls) {
		pnClsMapper.add(pnCls);
	}

	public boolean exists(PnClsModel pnCls) {
		List<PnClsModel> list = queryList(pnCls);
		return list.size() != 0;
	}
}
