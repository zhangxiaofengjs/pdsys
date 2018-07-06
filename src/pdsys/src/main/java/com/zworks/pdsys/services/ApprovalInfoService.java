package com.zworks.pdsys.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.enumClass.ApprovalState;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.mappers.ApprovalInfoMapper;
import com.zworks.pdsys.models.ApprovalInfoModel;
import com.zworks.pdsys.models.ApprovalNodeModel;
import com.zworks.pdsys.models.UserModel;

@Service
public class ApprovalInfoService {
	@Autowired
    private ApprovalInfoMapper approvalInfoMapper;
	
	public ApprovalInfoModel queryById(Integer id) {
		ApprovalInfoModel info = new ApprovalInfoModel();
		info.setId(id);
		List<ApprovalInfoModel> list = queryList(info);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public List<ApprovalInfoModel> queryList(ApprovalInfoModel info) {
		List<ApprovalInfoModel> list = approvalInfoMapper.queryList(info);
		return list;
	}

	public boolean needApproval(ApprovalInfoModel info) {
		if(info.getState() == ApprovalState.WORKING.ordinal()) {
			//还未提交审批
			return false;
		} else if(info.getState() == ApprovalState.WAIT.ordinal()) {
			//等待批复
			return true;
		} else if(info.getState() == ApprovalState.OK.ordinal()) {
			ApprovalNodeModel node = info.getNode();
			ApprovalNodeModel nextNode = node.getNextNode();
			if(nextNode != null) {
				//已经批复OK,还有下一节点
				return true;
			}
			return false;
		} else {
			//被驳回的，不需要继续审批
			return false;
		}
	}

	public void add(ApprovalInfoModel info) {
		approvalInfoMapper.add(info);
	}

	@Transactional
	public void requestApproval(ApprovalInfoModel approvalInfo) {
		approvalInfo.getFilterCond().put("updateState", true);
		approvalInfo.getFilterCond().put("updateRequestUser", true);
		approvalInfo.setState(ApprovalState.WAIT.ordinal());
		UserModel user = SecurityContextUtils.getLoginUser().getUser();
		approvalInfo.setRequestUser(user);
		approvalInfoMapper.update(approvalInfo);
	}

	/**
	 * 批准或驳回
	 * @param approvalInfo
	 * @param isOK
	 * @return
	 * true 批复结束
	 * false 还要继续批复
	 */
	public boolean responseApproval(ApprovalInfoModel approvalInfo, boolean isOK) {
		approvalInfo.getFilterCond().put("updateState", true);
		approvalInfo.getFilterCond().put("updateApprovalUser", true);
		ApprovalNodeModel node = approvalInfo.getNode();
		ApprovalNodeModel nextNode = node.getNextNode();
		if(isOK) {
			approvalInfo.setState(ApprovalState.OK.ordinal());
			
			if(nextNode != null) {
				//如果有下一个节点，则准备下一步批复
				approvalInfo.setNode(nextNode);
				approvalInfo.getFilterCond().put("updateNode", true);
			}
		} else {
			approvalInfo.setState(ApprovalState.NG.ordinal());
		}
		approvalInfo.setApprovalUser(SecurityContextUtils.getLoginUser().getUser());
		approvalInfoMapper.update(approvalInfo);
		
		if(isOK && nextNode == null) {
			//批复OK且没有下一节点批复
			return true;
		}
		return false;
	}

	public boolean isLoginUserApprovalable(ApprovalInfoModel approvalInfo) {
		UserModel user = SecurityContextUtils.getLoginUser().getUser();
		ApprovalNodeModel node = approvalInfo.getNode();
		List<UserModel> approvalUsers = node.getApprovalUsers();
		for(UserModel u : approvalUsers) {
			if(u.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}
}
