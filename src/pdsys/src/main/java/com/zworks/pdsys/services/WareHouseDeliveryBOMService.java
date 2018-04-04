package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/01
 */
@Service
public class WareHouseDeliveryBOMService {
	@Autowired
    private WareHouseDeliveryBOMMapper wareHouseDeliveryBOMMapper;
	
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	public List<WareHouseDeliveryBOMModel> queryList(WareHouseDeliveryBOMModel obj) {
		return wareHouseDeliveryBOMMapper.queryList(obj);
	}

	@Transactional
	public void addOrUpdate(String strIds, WareHouseDeliveryBOMModel wareHouseDeliveryBOM) {
		List<Integer> whBomIds = StringUtils.split(strIds);
		
		for(int whBomId : whBomIds) {
			WareHouseBOMModel wareHouseBOM = wareHouseDeliveryBOM.getWareHouseBOM();
			wareHouseBOM.setId(whBomId);
			
			WareHouseDeliveryModel wareHouseDelivery =  wareHouseDeliveryBOM.getWareHouseDelivery();
			
			List<WareHouseDeliveryModel> deliverys = wareHouseDeliveryService.queryList(wareHouseDelivery);
			if(deliverys == null || deliverys.size() == 0) {
				//该领收人还未创建输出单的话，先创建
				wareHouseDeliveryService.add(wareHouseDelivery);
			} else {
				WareHouseDeliveryModel wareHouseDeliveryExist = deliverys.get(0);
				wareHouseDelivery.setId(wareHouseDeliveryExist.getId());
			}
			
			List<WareHouseDeliveryBOMModel> whDeliveryBoms = wareHouseDeliveryBOMMapper.queryList(wareHouseDeliveryBOM);
			if(whDeliveryBoms == null || whDeliveryBoms.size() == 0) {
				//该领收人还未创建输出单的话，先创建
				wareHouseDeliveryBOMMapper.add(wareHouseDeliveryBOM);
			} else {
				WareHouseDeliveryBOMModel wareHouseDeliveryBOMExist = whDeliveryBoms.get(0);
				wareHouseDeliveryBOM.setId(wareHouseDeliveryBOMExist.getId());
				wareHouseDeliveryBOMMapper.update(wareHouseDeliveryBOM);
			}
		}
	}
}
