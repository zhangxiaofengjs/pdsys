package com.zworks.pdsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zworks.pdsys.mappers.PurchaseBOMMapper;
import com.zworks.pdsys.models.PurchaseBOMModel;

@Service
public class PurchaseBOMService {
	@Autowired
    private PurchaseBOMMapper purchaseBOMMapper;
	
	public void delete(PurchaseBOMModel purchaseBOM) {
		purchaseBOMMapper.delete( purchaseBOM );
	}
}
