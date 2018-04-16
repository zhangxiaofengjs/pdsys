package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.WareHouseMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Mapper
public interface WareHouseMachinePartMapper {
	
	List<WareHouseMachinePartModel> queryList(WareHouseMachinePartModel filterObj);
}
