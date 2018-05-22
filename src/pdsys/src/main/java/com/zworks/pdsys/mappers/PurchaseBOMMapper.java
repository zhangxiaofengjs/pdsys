package com.zworks.pdsys.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PurchaseBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/14
 */
@Mapper
public interface PurchaseBOMMapper {

	void delete(PurchaseBOMModel purchaseBOM);
	
	void add(PurchaseBOMModel purchaseBOM);

	void update(PurchaseBOMModel purchaseBom);
}
