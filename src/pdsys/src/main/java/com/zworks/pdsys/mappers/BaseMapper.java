package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.BOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/25
 */
@Mapper
public interface BaseMapper {
	
	List<BOMModel> queryList(BOMModel customer);

	void add(BOMModel filterObj);

	void update(BOMModel filterObj);

	void addSupplier(BOMModel bom);

	void deleteSupplier(BOMModel bom);
}
