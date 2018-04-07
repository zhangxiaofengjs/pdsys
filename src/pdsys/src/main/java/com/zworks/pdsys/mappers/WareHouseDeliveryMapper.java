package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/03
 */
@Mapper
public interface WareHouseDeliveryMapper {
	
	void add(WareHouseDeliveryModel obj);

	List<WareHouseDeliveryModel> queryList(WareHouseDeliveryModel obj);

	void update(WareHouseDeliveryModel delivery);

	void delete(WareHouseDeliveryModel delivery);
}
