package com.zworks.pdsys.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.NoticeModel;

@Mapper
public interface NoticeMapper {

	void add(NoticeModel s);
	
	List<NoticeModel> queryList(NoticeModel n);
	
	int selectPageListCount(Map<String,Object> map);

	void update(NoticeModel n);
}
