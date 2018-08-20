package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;

@Mapper
public interface OrderPnMapper {
	
	List<OrderPnModel> queryList( OrderPnModel orderPn );
	
	void update(OrderPnModel orderPnModel);
	
	void add(OrderPnModel orderPn);
	
	void delete(OrderPnModel orderPn);
	
	List<BOMUseNumBean> queryBomList(OrderModel order);
	
	List<OrderPnModel> queryOrderPns(OrderPnModel orderPn);
}
