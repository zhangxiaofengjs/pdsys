package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Mapper
public interface WareHousePnMapper {
	
	List<WareHousePnModel> queryList(WareHousePnModel filterObj);
}
