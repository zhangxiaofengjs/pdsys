package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.OrderPnModel;

@Mapper
public interface OrderPnMapper {
	
	List<OrderPnModel> queryList( OrderPnModel orderPnModel );
}
