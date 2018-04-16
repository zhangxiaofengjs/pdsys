package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;

@Mapper
public interface PnClsMapper {

	List<PnClsModel> queryList(PnClsModel pnClsModel);

	void add(PnClsModel pnCls);
	
}
