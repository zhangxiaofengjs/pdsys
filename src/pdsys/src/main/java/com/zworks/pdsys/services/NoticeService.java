package com.zworks.pdsys.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.utils.Page;
import com.zworks.pdsys.mappers.NoticeMapper;
import com.zworks.pdsys.models.NoticeModel;

@Service
public class NoticeService {
	@Autowired
    private NoticeMapper noticeMapper;
	
	public void add(List<NoticeModel> ss) {
		noticeMapper.add(ss);
	}
	
	public Map<String, Object> queryList( NoticeModel notice,int pageon ) {
		Map<String,Object> map=new HashMap<String, Object>();
		Page page = new Page(pageon);
		page.setRowcountAndCompute(noticeMapper.selectPageListCount(null));
		map.put("notice", notice);
		map.put("page", page);
		
		List<NoticeModel> list = noticeMapper.queryList(map);
		map.put("list", list);
		
		return map;
	}
	
	public boolean checkIsExit(String no)
	{
		boolean isExist = false;
		NoticeModel nm = new NoticeModel();
		nm.setComment(no);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("notice", nm);
		List<NoticeModel> list = noticeMapper.queryList(map);
		
		if( list!=null && list.size()>0 )
			isExist = true;
		
		return isExist;
	}

}
