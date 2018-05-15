package com.zworks.pdsys.services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.models.OrderModel;

@Service
public class OrderService {
	@Autowired
    private OrderMapper orderMapper;
	
	public List<OrderModel> queryList( OrderModel orderModel ) {
		return orderMapper.queryList( orderModel );
	}
	
	public void updateOrderState(OrderModel orderModel) {
		orderMapper.updateOrderState( orderModel );
	}
	
	public void updateOrder(OrderModel orderModel) {
		orderMapper.updateOrder( orderModel );
	}
	
	public OrderModel queryOne(OrderModel order) {
		List<OrderModel> list = queryList(order);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public void save(OrderModel orderModel) {
		orderMapper.save( orderModel );
	}
}
