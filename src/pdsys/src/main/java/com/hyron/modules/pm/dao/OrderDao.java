package com.hyron.modules.pm.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.modules.pm.entity.OrderEntity;
import com.hyron.modules.sys.dao.BaseDao;

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
