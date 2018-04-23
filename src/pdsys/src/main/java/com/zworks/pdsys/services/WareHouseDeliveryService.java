package com.zworks.pdsys.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.enumClass.DeliveryType;
import com.zworks.pdsys.mappers.WareHouseDeliveryMapper;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/03
 */
@Service
public class WareHouseDeliveryService {
	@Autowired
    private WareHouseDeliveryMapper wareHouseDeliveryMapper;
	@Autowired
	private WareHousePnService wareHousePnService;
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	@Autowired
	private WareHouseDeliveryPnService wareHouseDeliveryPnService;
	
	public List<WareHouseDeliveryModel> queryList(WareHouseDeliveryModel obj) {
		return wareHouseDeliveryMapper.queryList(obj);
	}
	
	public List<WareHouseDeliveryModel> queryListWithPn(WareHouseDeliveryModel delivery) {
		return wareHouseDeliveryMapper.queryListWithPn(delivery);
	}
	
	public List<WareHouseDeliveryModel> queryListWithBOM(WareHouseDeliveryModel delivery) {
		return wareHouseDeliveryMapper.queryListWithBOM(delivery);
	}
	
	public WareHouseDeliveryModel queryOne(WareHouseDeliveryModel obj) {
		List<WareHouseDeliveryModel> list = wareHouseDeliveryMapper.queryList(obj);
		
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
	public WareHouseDeliveryModel queryOneWithPn(WareHouseDeliveryModel obj) {
		List<WareHouseDeliveryModel> list = wareHouseDeliveryMapper.queryListWithPn(obj);
		
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
	public WareHouseDeliveryModel queryOneWithBOM(WareHouseDeliveryModel obj) {
		List<WareHouseDeliveryModel> list = wareHouseDeliveryMapper.queryListWithBOM(obj);
		
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
	
	public void add(WareHouseDeliveryModel obj) {
		wareHouseDeliveryMapper.add(obj);
	}
	public void delete(WareHouseDeliveryModel delivery) {
		delivery.setState(DeliveryState.DELETE.ordinal());
		wareHouseDeliveryMapper.update(delivery);
	}
	
	@Transactional
	public void deleteForever(WareHouseDeliveryModel delivery) {
		wareHouseDeliveryPnService.delete(delivery.getWareHouseDeliveryPns());
		wareHouseDeliveryMapper.delete(delivery);
	}
	
	@Transactional
	public boolean delivery(WareHouseDeliveryModel delivery) {
		if(delivery.getType() == (int)DeliveryType.PN.ordinal()) {
			delivery = this.queryOneWithPn(delivery);
			for(WareHouseDeliveryPnModel deliveryBOM : delivery.getWareHouseDeliveryPns()) {
				WareHousePnModel wareHousePn = deliveryBOM.getWareHousePn();
				
				float semiNum = -1, num = -1;
				if(wareHousePn != null) {
					semiNum = wareHousePn.getSemiProducedNum() - deliveryBOM.getSemiProducedNum();
					num = wareHousePn.getProducedNum() - deliveryBOM.getProducedNum();
				}
				
				if(num < 0 || semiNum < 0) {
					//库存不足
					return false;
				}
				wareHousePn.setProducedNum(num);
				wareHousePn.setSemiProducedNum(num);
				
				wareHousePnService.update(wareHousePn);
			}
		} else if(delivery.getType() == (int)DeliveryType.BOM.ordinal()) {
			delivery = this.queryOneWithBOM(delivery);
			for(WareHouseDeliveryBOMModel deliveryBOM : delivery.getWareHouseDeliveryBOMs()) {
				WareHouseBOMModel wareHouseBOM = deliveryBOM.getWareHouseBOM();
				
				float num = -1;
				if(wareHouseBOM != null) {
					num = wareHouseBOM.getNum() - deliveryBOM.getNum();
				}
				
				if(num < 0) {
					//库存不足
					return false;
				}
				wareHouseBOM.setNum(num);
				
				wareHouseBOMService.update(wareHouseBOM);
			}
		} else {
			return false;
		}
		
		delivery.setDeliveryTime(new Date());
		delivery.setState(DeliveryState.DELIVERIED.ordinal());
		
		wareHouseDeliveryMapper.update(delivery);
		return true;
	}
}