package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.DeviceRepairModel;

@Mapper
public interface DeviceRepairMapper {
	
	List<DeviceRepairModel> showRepairInfos(DeviceRepairModel deviceRepair);
	
    void add(DeviceRepairModel deviceRepair);
}
