package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.NoticeMapper;
import com.zworks.pdsys.models.NoticeModel;

@Service
public class NoticeService {
	@Autowired
    private NoticeMapper noticeMapper;
	
	public void add(List<NoticeModel> ss) {
		noticeMapper.add(ss);
	}
	
	public List<NoticeModel> queryList( NoticeModel notice ) {
		return noticeMapper.queryList( notice );
	}

}
