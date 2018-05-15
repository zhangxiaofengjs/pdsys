package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.PurchaseBOMMapper;
import com.zworks.pdsys.models.PurchaseBOMModel;

@Service
public class PurchaseBOMService {
	@Autowired
    private PurchaseBOMMapper purchaseBOMMapper;
	
	@Transactional
	public void delete(List<PurchaseBOMModel> purchaseBoms) {
		for(int i =0;i<purchaseBoms.size();i++)
		{
			PurchaseBOMModel pb = purchaseBoms.get(i);
			purchaseBOMMapper.delete( pb );
		}
		
	}
	
	public void addPB(PurchaseBOMModel purchaseBom) {
		purchaseBOMMapper.addPB( purchaseBom );
		
	}
}
