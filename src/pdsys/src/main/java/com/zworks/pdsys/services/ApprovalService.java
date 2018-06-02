package com.zworks.pdsys.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.enumClass.ApprovalState;
import com.zworks.pdsys.mappers.ApprovalNodeMapper;
import com.zworks.pdsys.models.ApprovalNodeModel;

@Service
public class ApprovalService {
	@Autowired
    private ApprovalNodeMapper approvalMapper;
	private Map<Integer, ApprovalNodeModel> approvalCacheMap = new HashMap<Integer, ApprovalNodeModel>();

	public ApprovalNodeModel queryById(Integer id) {
		ApprovalNodeModel node = approvalCacheMap.get(id);
		if(node == null) {
			node = new ApprovalNodeModel();
			node.setId(id);
			List<ApprovalNodeModel> list = queryList(node);
			if(list.size() == 1) {
				node = list.get(0);
				approvalCacheMap.put(id, node);
			}
		}
		return node;
	}
	
	public List<ApprovalNodeModel> queryList(ApprovalNodeModel node) {
		//TODO 后续的节点也应该一下子搜索出来
		List<ApprovalNodeModel> list = approvalMapper.queryList(node);
		return list;
	}
}
