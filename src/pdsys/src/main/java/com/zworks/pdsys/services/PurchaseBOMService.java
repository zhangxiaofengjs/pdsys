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
	public void add(List<PurchaseBOMModel> purchaseBoms) {
		for(PurchaseBOMModel pb : purchaseBoms) {
			purchaseBOMMapper.add( pb );
		}
	}
	
	@Transactional
	public void delete(List<PurchaseBOMModel> purchaseBoms) {
		for(int i = 0;i<purchaseBoms.size();i++)
		{
			PurchaseBOMModel pb = purchaseBoms.get(i);
			purchaseBOMMapper.delete( pb );
		}
	}
	
	public void update(PurchaseBOMModel purchaseBom) {
		purchaseBOMMapper.update(purchaseBom);
	}
	
	public void add(PurchaseBOMModel purchaseBom) {
		purchaseBOMMapper.add( purchaseBom );
	}
	
	public List<PurchaseBOMModel> queryList(PurchaseBOMModel purchaseBom) {
		return purchaseBOMMapper.queryList(purchaseBom);
	}
	
	public PurchaseBOMModel queryOne(PurchaseBOMModel purchaseBom) {
		List<PurchaseBOMModel> list = queryList(purchaseBom);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
}
