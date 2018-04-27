package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/03
 */
@Mapper
public interface WareHouseDeliveryMapper {
	List<WareHouseDeliveryModel> queryList(WareHouseDeliveryModel delivery);
	List<WareHouseDeliveryModel> queryListWithPn(WareHouseDeliveryModel delivery);
	List<WareHouseDeliveryModel> queryListWithBOM(WareHouseDeliveryModel delivery);
	List<WareHouseDeliveryModel> queryListWithMachinePart(WareHouseDeliveryModel delivery);

	void add(WareHouseDeliveryModel obj);

	void update(WareHouseDeliveryModel delivery);

	void delete(WareHouseDeliveryModel delivery);

}
