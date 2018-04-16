package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.CustomerMapper;
import com.zworks.pdsys.models.CustomerModel;

@Service
public class CustomerService {
	@Autowired
    private CustomerMapper customerMapper;
	
	public List<CustomerModel> queryList(CustomerModel customer) {
		return customerMapper.queryList(customer);
	}

	public CustomerModel queryById(int id) {
		CustomerModel c = new CustomerModel();
		c.setId(id);
		List<CustomerModel> cs = queryList(c);
		
		if(cs.size() ==1) {
			return cs.get(0);
		}
		return null;
	}
	
	public void add(CustomerModel filterObj) {
		customerMapper.add(filterObj);
	}
	public void update(CustomerModel filterObj) {
		customerMapper.update(filterObj);
	}
}
