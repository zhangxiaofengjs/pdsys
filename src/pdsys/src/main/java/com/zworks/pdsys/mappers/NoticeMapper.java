package com.zworks.pdsys.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.NoticeModel;

@Mapper
public interface NoticeMapper {

	void add(List<NoticeModel> ss);
	
	List<NoticeModel> queryList( Map<String,Object> map );
	
	int selectPageListCount(Map<String,Object> map);
}
