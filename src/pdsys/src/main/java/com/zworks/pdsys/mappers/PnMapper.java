package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;

@Mapper
public interface PnMapper {
	
	List<PnModel> queryList(PnModel pn);
	
	List<PnClsModel> queryClsList( PnModel pn );

	void add(PnModel pn);

	void update(PnModel pn);

	void addPnCls(PnModel pn);

	void addBOM(PnModel pn);

	void updateBOM(PnModel pn);
}
