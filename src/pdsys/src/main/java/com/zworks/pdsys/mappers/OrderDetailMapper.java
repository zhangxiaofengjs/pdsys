package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.OrderModel;

@Mapper
public interface OrderDetailMapper {
	
	List<OrderModel> queryList( OrderModel orderModel );
	
	void delete(OrderModel orderModel);
	
	OrderModel queryObject(int id);
}
