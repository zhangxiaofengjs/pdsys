package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.MachineTroubleModel;

@Mapper
public interface MachineTroubleMapper {
	
	List<MachineTroubleModel> queryList(MachineTroubleModel filterObj);

	void add(MachineTroubleModel machineTrouble);

}
