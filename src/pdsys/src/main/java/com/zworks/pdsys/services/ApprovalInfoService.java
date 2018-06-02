package com.zworks.pdsys.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.enumClass.ApprovalState;
import com.zworks.pdsys.mappers.ApprovalInfoMapper;
import com.zworks.pdsys.models.ApprovalInfoModel;
import com.zworks.pdsys.models.ApprovalNodeModel;

@Service
public class ApprovalInfoService {
	@Autowired
    private ApprovalInfoMapper approvalInfoMapper;

	public ApprovalInfoModel queryById(Integer id) {
		ApprovalInfoModel info = new ApprovalInfoModel();
		info.setId(id);
		List<ApprovalInfoModel> list = queryList(info);
		if(list.size() == 1) {
			info = list.get(0);
		}
		return null;
	}
	
	public List<ApprovalInfoModel> queryList(ApprovalInfoModel info) {
		List<ApprovalInfoModel> list = approvalInfoMapper.queryList(info);
		return list;
	}

	public boolean needApproval(ApprovalInfoModel info) {
		if(info.getState() == ApprovalState.OK.ordinal()) {
			ApprovalNodeModel node = info.getNode();
			ApprovalNodeModel nextNode = node.getNextNode();
			if(nextNode == null) {
				//本节点OK，且没有下一节点
				return true;
			}
		}
		return false;
	}

	public void add(ApprovalInfoModel info) {
		approvalInfoMapper.add(info);
	}
}
