package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.BOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Mapper
public interface BOMMapper {
	
	List<BOMModel> queryList(BOMModel customer);

	void add(BOMModel filterObj);

	void update(BOMModel filterObj);
}
