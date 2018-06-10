package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;

@Service
public class OrderService {
	@Autowired
    private OrderMapper orderMapper;
	
	public List<OrderModel> queryList( OrderModel order ) {
		return orderMapper.queryList( order );
	}
	
	public void update(OrderModel order) {
		orderMapper.update( order );
	}
	
	public OrderModel queryOne(OrderModel order) {
		List<OrderModel> list = queryList(order);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public void save(OrderModel order) {
		orderMapper.save( order );
	}

	public boolean isAllPnDelivered(OrderModel o) {
		OrderModel order = queryOne(o);
		List<OrderPnModel> pns = order.getOrderPns();
		
		for(OrderPnModel pn : pns) {
			if(pn.getDeliveredNum() >= pn.getNum()) {
				return false;
			}
		}
		return true;
	}
}
