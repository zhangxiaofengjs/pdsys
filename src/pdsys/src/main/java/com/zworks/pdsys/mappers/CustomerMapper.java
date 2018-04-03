package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.CustomerModel;

/**
 * @author: ZHAI
 * @version: 2018/03/30
 */
@Mapper
public interface CustomerMapper {
	
	List<CustomerModel> queryList(CustomerModel customer);
}
