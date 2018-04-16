package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/15
 */
@Mapper
public interface MachinePartMapper {

	List<MachinePartModel> queryList(MachinePartModel filterObj);

	void add(MachinePartModel machinePart);

	void update(MachinePartModel machinePart);
}
