package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Mapper
public interface WareHouseDeliveryPnMapper {
	
	void add(WareHouseDeliveryPnModel wareHouseDeliveryPn);

	List<WareHouseDeliveryPnModel> queryList(WareHouseDeliveryPnModel wareHouseDeliveryPn);

	void update(WareHouseDeliveryPnModel wareHouseDeliveryPn);

	void delete(WareHouseDeliveryPnModel deliveryPn);
}
