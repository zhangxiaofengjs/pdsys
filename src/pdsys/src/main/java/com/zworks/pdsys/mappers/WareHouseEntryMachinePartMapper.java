package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/25
 */
@Mapper
public interface WareHouseEntryMachinePartMapper {
	
	void add(WareHouseEntryMachinePartModel entryMachinePart);
	void delete(WareHouseEntryMachinePartModel entryMachinePart);
	void update(WareHouseEntryMachinePartModel entryMachinePart);
	List<WareHouseEntryMachinePartModel> queryList(WareHouseEntryMachinePartModel entryMachinePart);
}
