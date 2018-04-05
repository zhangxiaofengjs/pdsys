package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/01
 */
@Mapper
public interface WareHouseDeliveryBOMMapper {
	
	int add(WareHouseDeliveryBOMModel wareHouseDeliveryBOM);

	List<WareHouseDeliveryBOMModel> queryList(WareHouseDeliveryBOMModel wareHouseDeliveryBOM);

	void update(WareHouseDeliveryBOMModel wareHouseDeliveryBOM);
}
