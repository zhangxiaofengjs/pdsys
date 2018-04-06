package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.mappers.WareHouseDeliveryMapper;
import com.zworks.pdsys.mappers.WareHouseEntryMapper;
import com.zworks.pdsys.mappers.WareHouseEntryPnMapper;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Service
public class WareHouseEntryPnService {
	@Autowired
    private WareHouseEntryPnMapper wareHouseEntryPnMapper;
	
	public void add(WareHouseEntryPnModel entryPn) {
		wareHouseEntryPnMapper.add(entryPn);
	}

	@Transactional
	public void delete(List<WareHouseEntryPnModel> entryPns) {
		for(WareHouseEntryPnModel entryPn : entryPns) {
			wareHouseEntryPnMapper.delete(entryPn);
		}
	}
}
