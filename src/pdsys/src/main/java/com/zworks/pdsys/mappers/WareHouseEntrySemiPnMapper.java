package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Mapper
public interface WareHouseEntrySemiPnMapper {
	
	void add(WareHouseEntrySemiPnModel entryPn);
	void delete(WareHouseEntrySemiPnModel entryPn);
	void update(WareHouseEntrySemiPnModel entryPn);
	List<WareHouseEntrySemiPnModel> queryList(WareHouseEntrySemiPnModel entryPn);
}
