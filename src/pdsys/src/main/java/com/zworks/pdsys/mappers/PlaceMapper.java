package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PlaceModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/08
 */
@Mapper
public interface PlaceMapper {
	
	List<PlaceModel> queryList(PlaceModel filterObj);
}
