package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.enumClass.NoticeState;
import com.zworks.pdsys.mappers.NoticeMapper;
import com.zworks.pdsys.models.NoticeModel;

@Service
public class NoticeService {
	@Autowired
    private NoticeMapper noticeMapper;
	
	public void add(NoticeModel s) {
		String content = s.getContent();
		if(content.length() > 1024) {
			s.setContent(content.substring(0, 1023));
		}
		noticeMapper.add(s);
	}
	
	public List<NoticeModel> queryList(NoticeModel notice) {
//		Page page = new Page(pageon);
//		page.setRowcountAndCompute(noticeMapper.selectPageListCount(null));
//		map.put("notice", notice);
//		map.put("page", page);
//		
		List<NoticeModel> list = noticeMapper.queryList(notice);
		//map.put("list", list);
		
		return list;
	}
	
	public NoticeModel queryOne(NoticeModel n) {
		List<NoticeModel> list = queryList(n);
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	public boolean exist(NoticeModel n) {
		List<NoticeModel> list = queryList(n);
		if(list.size()!=0) {
			return true;
		}
		return false;
	}

	public void toggleState(NoticeModel notice) {
		NoticeModel n = queryOne(notice);
		
		if(n.getState() == NoticeState.Read.ordinal()) {
			n.setState(NoticeState.Unread.ordinal());
		} else {
			n.setState(NoticeState.Read.ordinal());
		}
		
		noticeMapper.update(n);
	}

}
