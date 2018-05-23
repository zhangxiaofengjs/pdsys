package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.NoticeModel;

@Mapper
public interface NoticeMapper {

	void add(List<NoticeModel> ss);
	
	List<NoticeModel> queryList( NoticeModel notice );
}
