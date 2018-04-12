package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.mappers.PnMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;

@Service
public class PnService {
	@Autowired
    private PnMapper pnMapper;
	
	public List<PnModel> queryList() {
		return pnMapper.queryList();
	}
	
	public List<PnClsModel> queryClsList( PnModel pn ) {
		return pnMapper.queryClsList( pn );
	}
	
	public void save(OrderPnModel orderPn) {
		pnMapper.save( orderPn );
	}
	
	public void delete(OrderPnModel orderPn) {
		pnMapper.delete(orderPn);
	}
	
	public List<BomDetailModel> queryBomList(OrderModel order) {
		return pnMapper.queryBomList( order );
	}
	
}
