package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.entities.OrderEntity;

@Mapper
public interface OrderMapper {
	
	List<OrderEntity> queryList();
}
