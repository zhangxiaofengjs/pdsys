package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.form.beans.DeviceMaitenaceMachinePartsBean;
import com.zworks.pdsys.mappers.DeviceMapper;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineMachinePartRelModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Service
public class DeviceService {
	@Autowired
    private DeviceMapper deviceMapper;
	@Autowired
    private MachineService machineService;
	@Autowired
	private WareHouseMachinePartService wareHouseMachinePartService;
	
	public List<DeviceModel> queryList(DeviceModel filterObj) {
		List<DeviceModel> list = deviceMapper.queryList(filterObj);
		
		return list;
	}
	
	public DeviceModel queryOne(DeviceModel filterObj) {
		List<DeviceModel> list = deviceMapper.queryList(filterObj);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void update(DeviceModel device) {
		deviceMapper.update(device);
	}

	public void add(DeviceModel device) {
		deviceMapper.add(device);
	}

	public List<DeviceMaitenaceMachinePartsBean> getMaitenaceParts(List<Integer> deviceIds) {
		Map<Integer, DeviceMaitenaceMachinePartsBean> machinePartMap = new HashMap<Integer, DeviceMaitenaceMachinePartsBean>();
		
		for(int id : deviceIds) {
			DeviceModel device = new DeviceModel();
			device.setId(id);
			
			device = queryOne(device);
			if(device == null) {
				continue;
			}

			MachineModel machine = device.getMachine();
			machine = machineService.queryOne(machine);
			if(machine == null) {
				continue;
			}
			
			List<MachineMachinePartRelModel> partRels = machine.getMachineMachinePartRels();

			//对使用备品进行合计
			for(MachineMachinePartRelModel partRel : partRels) {
				MachinePartModel part = partRel.getMachinePart();
				
				int partId = part.getId();
				DeviceMaitenaceMachinePartsBean partBean = machinePartMap.get(partId);
				
				if(partBean == null) {//还未添加的先添加并设定库存
					partBean = new DeviceMaitenaceMachinePartsBean();
					
					partBean.setMachinePart(part);
					
					List<MachineModel> machines = new ArrayList<MachineModel>();
					partBean.setMachines(machines );
					
					WareHouseMachinePartModel whPart = wareHouseMachinePartService.queryByPartId(partId);
					if(whPart != null) {
						partBean.setWareHouseNum(whPart.getNum());
					}
					
					machinePartMap.put(partId, partBean);
				}
				
				//合计 使用机器以及使用量
				List<MachineModel> machines = partBean.getMachines();
				boolean contain = false;
				for(MachineModel m : machines) {
					if(m.getId() == machine.getId()) {
						contain = true;
					}
				}
				if(!contain) {
					machines.add(machine);
				}
				float maitenaceNum = partBean.getMaitenaceNum() + partRel.getMaitenacePartNum();
				
				partBean.setMaitenaceNum(maitenaceNum);
			}
		}
		
		List<DeviceMaitenaceMachinePartsBean> list = new ArrayList<DeviceMaitenaceMachinePartsBean>(machinePartMap.values());
		return list;
	}
}
