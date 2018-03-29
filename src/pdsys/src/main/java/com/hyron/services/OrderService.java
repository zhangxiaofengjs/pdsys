package com.hyron.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyron.entities.OrderEntity;
import com.hyron.mappers.OrderMapper;

@Service
public class OrderService {
	@Autowired
    private OrderMapper orderMapper;
	
	public List<OrderEntity> queryList() {
		return orderMapper.queryList();
	}
}
