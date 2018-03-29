package com.zworks.pdsys.modules.pm.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.modules.pm.entity.OrderEntity;
import com.zworks.pdsys.modules.sys.dao.BaseDao;

/**
 * 系统用户
 * 
 * @author ZHAI
 * @date 2018年3月28日 上午9:34:11
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {

	/**
	 * 根据订单号，查询订单
	 */
	OrderEntity queryByOrderNo(String orderNo);
}
