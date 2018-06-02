package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.ApprovalInfoModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/06/02
 */
@Mapper
public interface ApprovalInfoMapper {

	List<ApprovalInfoModel> queryList(ApprovalInfoModel info);

	void add(ApprovalInfoModel info);
}
