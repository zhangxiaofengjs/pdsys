package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.ApprovalNodeModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/06/02
 */
@Mapper
public interface ApprovalNodeMapper {

	List<ApprovalNodeModel> queryList(ApprovalNodeModel node);

	void add(ApprovalNodeModel node);

	void deleteApprovalUser(ApprovalNodeModel node);

	void addApprovalUser(ApprovalNodeModel node);
}
