package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Mapper
public interface WareHouseEntryPnMapper {
	
	void add(WareHouseEntryPnModel entryPn);
	void delete(WareHouseEntryPnModel entryPn);
}
