package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Mapper
public interface DeviceMapper {
	
	List<DeviceModel> queryList(DeviceModel filterObj);

	void update(DeviceModel device);

	void add(DeviceModel device);
}
