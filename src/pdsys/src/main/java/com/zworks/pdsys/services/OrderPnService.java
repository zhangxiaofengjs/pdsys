package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.beans.BomDetailModel;
import com.zworks.pdsys.mappers.OrderPnMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;

@Service
public class OrderPnService {
	@Autowired
    private OrderPnMapper orderPnMapper;
	
	public void save(OrderPnModel orderPn) {
		orderPnMapper.save( orderPn );
	}
	
	public void delete(OrderPnModel orderPn) {
		orderPnMapper.delete(orderPn);
	}
	
	public List<BomDetailModel> queryBomList(OrderModel order) {
		return orderPnMapper.queryBomList( order );
	}
	
	public List<OrderPnModel> queryPnByOrderPnId( OrderPnModel orderPn ){
		return orderPnMapper.queryPnByOrderPnId( orderPn );
	}
	
	public List<PnClsModel> queryClsByOrderPnId( OrderPnModel orderPn ){
		return orderPnMapper.queryClsByOrderPnId( orderPn );
	}
	
	public List<OrderPnModel> queryOrderPnList( OrderModel order ) {
		return orderPnMapper.queryList( order );
	}
	
	public void updateOrderPn(OrderPnModel orderPnModel) {
		orderPnMapper.updateOrderPn( orderPnModel );
	}

}
