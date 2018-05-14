package com.zworks.pdsys.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.enumClass.DeliveryType;
import com.zworks.pdsys.mappers.WareHouseDeliveryMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
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
	private WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	private OrderPnService orderPnService;
	
	public List<WareHouseDeliveryModel> queryList(WareHouseDeliveryModel obj) {
		return wareHouseDeliveryMapper.queryList(obj);
	}
	
	public boolean exists(WareHouseDeliveryModel d) {
		List<WareHouseDeliveryModel> list = queryList(d);
		if(list.size() != 0) {
			return true;
		}
		return false;
	}
	
	public List<WareHouseDeliveryModel> queryListWithPn(WareHouseDeliveryModel delivery) {
		return wareHouseDeliveryMapper.queryListWithPn(delivery);
	}
	
	public List<WareHouseDeliveryModel> queryListWithBOM(WareHouseDeliveryModel delivery) {
		return wareHouseDeliveryMapper.queryListWithBOM(delivery);
	}
	
	public List<WareHouseDeliveryModel> queryListWithMachinePart(WareHouseDeliveryModel delivery) {
		return wareHouseDeliveryMapper.queryListWithMachinePart(delivery);
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
	

	public WareHouseDeliveryModel queryOneWithMachinePart(WareHouseDeliveryModel delivery) {
		List<WareHouseDeliveryModel> list = wareHouseDeliveryMapper.queryListWithMachinePart(delivery);
		
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
	public boolean delivery(WareHouseDeliveryModel delivery) {
		delivery.getFilterCond().put("LOCKUPDATE", true);
		
		if(delivery.getType() == (int)DeliveryType.PN.ordinal()) {
			delivery = this.queryOneWithPn(delivery);
			if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
				//已经被其他人出库过
				return false;
			}
			Map<Integer, Float> pnDeliverySemiNumMap = new HashMap<Integer, Float>(); 
			Map<Integer, Float> pnDeliveryNumMap = new HashMap<Integer, Float>(); 

			//搜集各出库品番数量(因为是按照order的，所以品番可能重复在不同的order中)
			for(WareHouseDeliveryPnModel deliveryPn : delivery.getWareHouseDeliveryPns()) {
				PnModel pn = deliveryPn.getPn();
				int pnId = pn.getId();
				
				float semiNum = 0, num = 0;
				if(pnDeliverySemiNumMap.containsKey(pnId)) {
					semiNum = pnDeliverySemiNumMap.get(pnId);
				}
				pnDeliverySemiNumMap.put(pnId, semiNum + deliveryPn.getSemiProducedNum());
				
				if(pnDeliveryNumMap.containsKey(pnId)) {
					num = pnDeliveryNumMap.get(pnId);
				}
				pnDeliveryNumMap.put(pnId, num + deliveryPn.getProducedNum());
				
				//更新出库数量
				OrderPnModel opn = new OrderPnModel();
				opn.setOrder(deliveryPn.getOrder());
				opn.setPn(deliveryPn.getPn());
				opn.setDeliveredNum(deliveryPn.getProducedNum());
				opn.getFilterCond().put("update_delivery_num", true);
				orderPnService.update(opn);
			}
			
			//准备更新
			PnModel pn = new PnModel();
			WareHousePnModel whPn = new WareHousePnModel();
			whPn.setPn(pn);
			whPn.getFilterCond().put("LOCKUPDATE", true);
			for (Integer pnId : pnDeliverySemiNumMap.keySet()) {
				pn.setId(pnId);
				WareHousePnModel wareHousePn = wareHousePnService.queryOne(whPn);
				if(wareHousePn == null) {
					return false;//库存不够
				}
				
				float semiNum = wareHousePn.getSemiProducedNum() - pnDeliverySemiNumMap.get(pnId);
				float num = wareHousePn.getProducedNum() - pnDeliveryNumMap.get(pnId);
				if(num < 0 || semiNum < 0) {
					//库存不足
					return false;
				}
				
				wareHousePn.setProducedNum(num);
				wareHousePn.setSemiProducedNum(semiNum);
				wareHousePnService.update(wareHousePn);
			}
		} else if(delivery.getType() == (int)DeliveryType.BOM.ordinal()) {
			delivery = this.queryOneWithBOM(delivery);
			if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
				//已经被其他人出库过
				return false;
			}
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
		} else if(delivery.getType() == (int)DeliveryType.MACHINEPART.ordinal()) {
			delivery = this.queryOneWithMachinePart(delivery);
			if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
				//已经被其他人出库过
				return false;
			}
			for(WareHouseDeliveryMachinePartModel deliveryMp : delivery.getWareHouseDeliveryMachineParts()) {
				WareHouseMachinePartModel wareHouseMP = deliveryMp.getWareHouseMachinePart();
				
				float num = -1;
				if(wareHouseMP != null) {
					num = wareHouseMP.getNum() - deliveryMp.getNum();
				}
				
				if(num < 0) {
					//库存不足
					return false;
				}
				wareHouseMP.setNum(num);
				
				wareHouseMachinePartService.update(wareHouseMP);
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