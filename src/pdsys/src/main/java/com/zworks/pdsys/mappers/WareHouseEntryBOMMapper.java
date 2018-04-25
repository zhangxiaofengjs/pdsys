package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseEntryBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/23
 */
@Mapper
public interface WareHouseEntryBOMMapper {
	
	void add(WareHouseEntryBOMModel entryBOM);
	void delete(WareHouseEntryBOMModel entryBOM);
	void update(WareHouseEntryBOMModel entryBOM);
	List<WareHouseEntryBOMModel> queryList(WareHouseEntryBOMModel entryBOM);
}
