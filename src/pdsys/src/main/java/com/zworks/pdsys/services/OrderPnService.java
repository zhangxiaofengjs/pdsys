package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.beans.BOMDetailModel;
import com.zworks.pdsys.mappers.OrderPnMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;

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
	
	public List<BOMDetailModel> queryBomList(OrderModel order) {
		return orderPnMapper.queryBomList( order );
	}
	
	public OrderPnModel queryOne( OrderPnModel orderPn ){
		List<OrderPnModel> ops = orderPnMapper.queryOrderPns(orderPn);
		if(ops.size() == 1) {
			return ops.get(0);
		}
		return null;
	}
	
	public List<OrderPnModel> queryOrderPnList( OrderModel order ) {
		return orderPnMapper.queryList( order );
	}
	
	public void update(OrderPnModel orderPnModel) {
		orderPnMapper.update( orderPnModel );
	}
	
	public boolean existsOrderPn(OrderPnModel orderPn) {
		List<OrderPnModel> ops = orderPnMapper.queryOrderPns(orderPn);
		if(ops.size()>0) {
			return true;
		}
		return false;
	}

}
