package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.OrderModel;

@Mapper
public interface OrderMapper {
	
	List<OrderModel> queryList( OrderModel orderModel );
	
	void updateOrderState(OrderModel orderModel);
	
	void save(OrderModel orderModel);
	
	void update(OrderModel orderModel);
}
