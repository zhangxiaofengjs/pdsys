package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.WareHouseBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Mapper
public interface WareHouseBOMMapper {
	
	List<WareHouseBOMModel> queryList(WareHouseBOMModel filterObj);
}
