package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.models.UnitModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Mapper
public interface UnitMapper {

	List<UnitModel> queryList(UnitModel unit);

	void add(UnitModel unit);
	
}
