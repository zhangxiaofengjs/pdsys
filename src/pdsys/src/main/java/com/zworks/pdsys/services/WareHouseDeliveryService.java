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
import com.zworks.pdsys.common.enumClass.OrderState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.mappers.WareHouseDeliveryMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseDeliverySemiPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;

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
	private WareHouseSemiPnService wareHouseSemiPnService;
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	@Autowired
	private WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	private OrderPnService orderPnService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	private WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	private WareHouseDeliveryMachinePartService wareHouseDeliveryMachinePartService;
	
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
	public List<WareHouseDeliveryModel> queryListWithSemiPn(WareHouseDeliveryModel delivery) {
		return wareHouseDeliveryMapper.queryListWithSemiPn(delivery);
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
	public WareHouseDeliveryModel queryOneWithSemiPn(WareHouseDeliveryModel obj) {
		List<WareHouseDeliveryModel> list = wareHouseDeliveryMapper.queryListWithSemiPn(obj);
		
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
		if(delivery.getType() == DeliveryType.PN.ordinal()) {
			WareHouseDeliveryPnModel ePn = new WareHouseDeliveryPnModel();
			ePn.setWareHouseDelivery(delivery);
			wareHouseDeliveryPnService.delete(ePn);
		}
		
		if(delivery.getType() == DeliveryType.BOM.ordinal()) {
			WareHouseDeliveryBOMModel eBOM = new WareHouseDeliveryBOMModel();
			eBOM.setWareHouseDelivery(delivery);
			wareHouseDeliveryBOMService.delete(eBOM);
		}
		
		if(delivery.getType() == DeliveryType.MACHINEPART.ordinal()) {
			WareHouseDeliveryMachinePartModel eMp = new WareHouseDeliveryMachinePartModel();
			eMp.setWareHouseDelivery(delivery);
			wareHouseDeliveryMachinePartService.delete(eMp);
		}
		
		wareHouseDeliveryMapper.delete(delivery);
	}
	
	@Transactional
	public void delivery(WareHouseDeliveryModel delivery) {
		delivery.getFilterCond().put("LOCKUPDATE", true);
		
		if(delivery.getType() == (int)DeliveryType.PN.ordinal()) {
			delivery = this.queryOneWithPn(delivery);
			if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
				//已经被其他人出库过
				throw new PdsysException("已经出库，刷新再试");
			}
			Map<Integer, Float> pnDeliveryNumMap = new HashMap<Integer, Float>(); 
			Map<Integer, OrderModel> orderMap = new HashMap<Integer, OrderModel>();
			
			//搜集各出库品番数量(因为是按照order的，所以品番可能重复在不同的order中)
			for(WareHouseDeliveryPnModel deliveryPn : delivery.getWareHouseDeliveryPns()) {
				PnModel pn = deliveryPn.getPn();
				int pnId = pn.getId();
				
				float num = 0;
				
				if(pnDeliveryNumMap.containsKey(pnId)) {
					num = pnDeliveryNumMap.get(pnId);
				}
				pnDeliveryNumMap.put(pnId, num + deliveryPn.getProducedNum());
				
				//搜集Order
				OrderModel order = deliveryPn.getOrder();
				orderMap.put(order.getId(), order);

				//更新出库数量
				OrderPnModel opn = new OrderPnModel();
				opn.setOrder(order);
				opn.setPn(deliveryPn.getPn());
				opn.setDeliveredNum(deliveryPn.getProducedNum());
				opn.getFilterCond().put("update_delivery_num", true);
				orderPnService.update(opn);
			}
			
			//更新库存
			PnModel pn = new PnModel();
			WareHousePnModel whPn = new WareHousePnModel();
			whPn.setPn(pn);
			whPn.getFilterCond().put("LOCKUPDATE", true);
			for (Integer pnId : pnDeliveryNumMap.keySet()) {
				pn.setId(pnId);
				WareHousePnModel wareHousePn = wareHousePnService.queryOne(whPn);
				if(wareHousePn == null) {
					throw new PdsysException("库存不足，刷新再试");//库存不够
				}
				
				float num = wareHousePn.getProducedNum() - pnDeliveryNumMap.get(pnId);
				if(num < 0) {
					//库存不足
					throw new PdsysException("库存不足，刷新再试");//库存不够
				}
				
				wareHousePn.setProducedNum(num);
				wareHousePnService.update(wareHousePn);
			}
			
			//更新订单状态
			OrderModel o = new OrderModel();
			for (Integer orderId : orderMap.keySet()) {
				o.setId(orderId);
				if(orderService.isAllPnDelivered(o)) {
					o.getFilterCond().put("update_state", true);
					o.setState(OrderState.FINISHED.ordinal());
					orderService.update(o);
				}
			}
		} else if(delivery.getType() == (int)DeliveryType.SEMIPN.ordinal()) {
			delivery = this.queryOneWithSemiPn(delivery);
			if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
				//已经被其他人出库过
				throw new PdsysException("已经出库，刷新再试");
			}
			for(WareHouseDeliverySemiPnModel deliveryPn : delivery.getWareHouseDeliverySemiPns()) {
				WareHouseSemiPnModel wareHousePn = deliveryPn.getWareHouseSemiPn();
				
				float num = -1;
				if(wareHousePn != null) {
					num = wareHousePn.getNum() - deliveryPn.getNum();
				}
				
				if(num < 0) {
					//库存不足
					throw new PdsysException("库存不足，刷新再试");//库存不够
				}
				wareHousePn.setNum(num);
				
				wareHouseSemiPnService.update(wareHousePn);
			}
		} else if(delivery.getType() == (int)DeliveryType.MACHINEPART.ordinal()) {
			delivery = this.queryOneWithMachinePart(delivery);
			if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
				//已经被其他人出库过
				throw new PdsysException("已经出库，刷新再试");
			}
			for(WareHouseDeliveryMachinePartModel deliveryMp : delivery.getWareHouseDeliveryMachineParts()) {
				WareHouseMachinePartModel wareHouseMP = deliveryMp.getWareHouseMachinePart();
				
				float num = -1;
				if(wareHouseMP != null) {
					num = wareHouseMP.getNum() - deliveryMp.getNum();
				}
				
				if(num < 0) {
					//库存不足
					throw new PdsysException("库存不足，刷新再试");//库存不够
				}
				wareHouseMP.setNum(num);
				
				wareHouseMachinePartService.update(wareHouseMP);
			}
		} else {
			throw new PdsysException("未设定处理");
		}
		
		delivery.setDeliveryTime(new Date());
		delivery.setState(DeliveryState.DELIVERIED.ordinal());
		
		wareHouseDeliveryMapper.update(delivery);
	}
	
	@Transactional
	public void deliveryBOM(WareHouseDeliveryModel d) {
		d.getFilterCond().put("LOCKUPDATE", true);
		WareHouseDeliveryModel delivery = this.queryOneWithBOM(d);

		if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
			//已经被其他人出库过
			throw new PdsysException("已经出库，刷新再试");
		}
		for(WareHouseDeliveryBOMModel deliveryBOM : delivery.getWareHouseDeliveryBOMs()) {
			WareHouseBOMModel wareHouseBOM = deliveryBOM.getWareHouseBOM();
			if(wareHouseBOM == null) {
				//库存不足
				throw new PdsysException("库存不足，刷新再试");
			}
			float num = wareHouseBOM.getNum() - deliveryBOM.getNum();
			float deliveryRemainingNum = wareHouseBOM.getDeliveryRemainingNum() + deliveryBOM.getNum();
			
			if(num < 0) {
				//库存不足
				throw new PdsysException("库存不足，刷新再试");
			}
			
			wareHouseBOM.setNum(num);
			wareHouseBOM.setDeliveryRemainingNum(deliveryRemainingNum);
			wareHouseBOM.getFilterCond().put("UPDATE_NUM", true);
			wareHouseBOM.getFilterCond().put("UPDATE_DELIVERYREMAINNUM", true);
			wareHouseBOMService.update(wareHouseBOM);
		}
		
		delivery.setDeliveryTime(new Date());
		delivery.setState(DeliveryState.DELIVERIED.ordinal());
		
		wareHouseDeliveryMapper.update(delivery);
	}
}