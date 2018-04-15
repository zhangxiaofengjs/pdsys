package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Mapper
public interface MachineMapper {
	
	List<MachineModel> queryList(MachineModel filterObj);

	void add(MachineModel machine);

	void update(MachineModel machine);
}
