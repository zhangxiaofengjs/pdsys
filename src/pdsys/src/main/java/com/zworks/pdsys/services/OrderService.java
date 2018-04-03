package com.zworks.pdsys.services;

import java.util.List;

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
	
	public void delete(OrderModel orderModel) {
		orderMapper.delete( orderModel );
	}
	
	public OrderModel queryObject(int id) {
		return orderMapper.queryObject( id );
	}
	
	public void save(OrderModel orderModel) {
		orderMapper.save( orderModel );
	}
}
