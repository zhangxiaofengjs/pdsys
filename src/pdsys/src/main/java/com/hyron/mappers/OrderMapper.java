package com.hyron.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.entities.OrderEntity;

@Mapper
public interface OrderMapper {
	
	List<OrderEntity> queryList();
}
