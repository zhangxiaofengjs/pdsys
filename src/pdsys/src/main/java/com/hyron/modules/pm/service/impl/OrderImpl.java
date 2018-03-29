//package com.hyron.modules.pm.service.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.hyron.modules.pm.dao.OrderDao;
//import com.hyron.modules.pm.entity.OrderEntity;
//import com.hyron.modules.pm.service.OrderService;
//
///**
// * 系统用户
// * 
// * @author ZHAI
// * @date 2018年03月28日 上午9:46:09
// */
//@Service("orderService")
//public class OrderImpl implements OrderService {
//	
//	@Autowired
//	private OrderDao orderDao;
//
//	@Override
//	public OrderEntity queryByOrderNo(String orderNo) {
//		return orderDao.queryByOrderNo(orderNo);
//	}
//
//	@Override
//	public OrderEntity queryObject(Long orderId) {
//		return orderDao.queryObject(orderId);
//	}
//
//	@Override
//	public List<OrderEntity> queryList(Map<String, Object> map) {
//		return orderDao.queryList(map);
//	}
//
//	@Override
//	public int queryTotal(Map<String, Object> map) {
//		return orderDao.queryTotal(map);
//	}
//
//	@Override
//	@Transactional
//	public void deleteBatch(Long[] orderIds) {
//		orderDao.deleteBatch(orderIds);
//	}
//}
