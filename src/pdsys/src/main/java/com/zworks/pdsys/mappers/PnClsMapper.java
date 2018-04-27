package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PnClsModel;

@Mapper
public interface PnClsMapper {

	List<PnClsModel> queryList(PnClsModel pnClsModel);

	void add(PnClsModel pnCls);
	
}
