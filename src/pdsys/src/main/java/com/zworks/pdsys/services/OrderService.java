package com.zworks.pdsys.services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.models.OrderModel;

@Service
public class OrderService {
	@Autowired
    private OrderMapper orderMapper;
	
	public List<OrderModel> queryList( OrderModel orderModel ) {
		return orderMapper.queryList( orderModel );
	}
	
	public void updateOrderState(OrderModel orderModel) {
		orderMapper.updateOrderState( orderModel );
	}
	
	public OrderModel queryOne(OrderModel order) {
		List<OrderModel> list = queryList(order);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public void save(OrderModel orderModel) {
		orderMapper.save( orderModel );
	}
	
	//去除重复的状态
	public int[] removeDuplicate(List<OrderModel> list) {
        Set<Integer> set=new TreeSet<Integer>();
        for (int i = 0; i < list.size(); i++) {
        	OrderModel o=list.get(i);
        	set.add(o.getState());
        }
        
        Integer[] arr2=set.toArray(new Integer[0]);
        int[] result=new int[arr2.length];
        
        for (int i = 0; i < result.length; i++) {
            result[i]=arr2[i];
        }
        return result;

	}
	
}
