package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PnModel;

@Mapper
public interface PnMapper {
	List<PnModel> queryList(PnModel pn);

	void add(PnModel pn);
	void update(PnModel pn);

	void addPnCls(PnModel pn);
	void deletePnCls(PnModel pn);

	void addBOM(PnModel pn);
	void deleteBOM(PnModel pn);
}
