package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.business.beans.BomDetailModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;

@Mapper
public interface OrderPnMapper {
	
	List<OrderPnModel> queryList( OrderModel order );
	
	void updateOrderPn(OrderPnModel orderPnModel);
	
	void save(OrderPnModel orderPn);
	
	void delete(OrderPnModel orderPn);
	
	List<BomDetailModel> queryBomList(OrderModel order);
	
	List<OrderPnModel> queryPnByOrderPnId( OrderPnModel orderPn );
	
	List<PnClsModel> queryClsByOrderPnId( OrderPnModel orderPn );
}
