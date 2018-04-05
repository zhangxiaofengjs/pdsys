package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseEntryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Mapper
public interface WareHouseEntryMapper {
	
	void add(WareHouseEntryModel obj);

	List<WareHouseEntryModel> queryList(WareHouseEntryModel obj);
}
