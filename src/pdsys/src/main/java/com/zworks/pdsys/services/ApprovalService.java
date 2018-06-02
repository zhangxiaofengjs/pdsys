package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.ApprovalMapper;
import com.zworks.pdsys.mappers.BOMMapper;
import com.zworks.pdsys.models.ApprovalNodeModel;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.SupplierModel;

@Service
public class ApprovalService {
	@Autowired
    private ApprovalMapper approvalMapper;

	public ApprovalNodeModel queryOne(ApprovalNodeModel node) {
		List<ApprovalNodeModel> list = queryList(node);
		//TODO 现在默认数据库只设定一条数据，所以正常工作
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public List<ApprovalNodeModel> queryList(ApprovalNodeModel node) {
		return approvalMapper.queryList(node);
	}
	
}
