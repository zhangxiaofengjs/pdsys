package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.OrderModel;

@Mapper
public interface OrderMapper {
	
	List<OrderModel> queryList( OrderModel orderModel );
	
	void updateOrderState(OrderModel orderModel);
	
	OrderModel queryObject(int id);
	
	void save(OrderModel orderModel);
}
