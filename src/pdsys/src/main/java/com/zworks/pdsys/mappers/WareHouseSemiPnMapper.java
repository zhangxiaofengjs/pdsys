package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseSemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Mapper
public interface WareHouseSemiPnMapper {
	
	List<WareHouseSemiPnModel> queryList(WareHouseSemiPnModel filterObj);

	void update(WareHouseSemiPnModel wareHousePn);

	void delete(WareHouseSemiPnModel wareHousePn);

	void add(WareHouseSemiPnModel wareHousePn);
}
