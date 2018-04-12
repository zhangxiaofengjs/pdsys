package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;

@Mapper
public interface PnMapper {
	
	List<PnModel> queryList();
	
	List<PnClsModel> queryClsList( PnModel pn );
	
	void save(OrderPnModel orderPn);
	
	void delete(OrderPnModel orderPn);
	
	List<BomDetailModel> queryBomList(OrderModel order);
}
