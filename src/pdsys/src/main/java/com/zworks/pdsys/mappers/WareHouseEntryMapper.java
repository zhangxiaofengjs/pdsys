package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseEntryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Mapper
public interface WareHouseEntryMapper {
	
	void add(WareHouseEntryModel obj);

	List<WareHouseEntryModel> queryList(WareHouseEntryModel obj);
	List<WareHouseEntryModel> queryListWithPn(WareHouseEntryModel obj);
	List<WareHouseEntryModel> queryListWithBOM(WareHouseEntryModel obj);
	List<WareHouseEntryModel> queryListWithMachinePart(WareHouseEntryModel obj);

	void update(WareHouseEntryModel entry);
}
