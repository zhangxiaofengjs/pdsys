package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PnClsModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/15
 */
@Mapper
public interface PnClsMapper {

	List<PnClsModel> queryList(PnClsModel pnClsModel);

	void add(PnClsModel pnCls);

	void delete(PnClsModel pnCls);

	void addBOM(PnClsModel pnCls);

	void deleteBOM(PnClsModel pnCls);
}
