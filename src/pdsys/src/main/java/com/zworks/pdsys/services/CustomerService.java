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
}
