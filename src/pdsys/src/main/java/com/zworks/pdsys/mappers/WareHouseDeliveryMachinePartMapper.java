package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Mapper
public interface WareHouseDeliveryMachinePartMapper {
	
	int add(WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePart);

	List<WareHouseDeliveryMachinePartModel> queryList(WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePart);

	void update(WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePart);
}
