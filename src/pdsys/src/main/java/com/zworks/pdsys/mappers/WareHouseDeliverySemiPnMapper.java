package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliverySemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Mapper
public interface WareHouseDeliverySemiPnMapper {
	
	void add(WareHouseDeliverySemiPnModel wareHouseDeliverySemiPn);

	List<WareHouseDeliverySemiPnModel> queryList(WareHouseDeliverySemiPnModel wareHouseDeliverySemiPn);

	void update(WareHouseDeliverySemiPnModel wareHouseDeliverySemiPn);

	void delete(WareHouseDeliverySemiPnModel deliveryPn);
}
