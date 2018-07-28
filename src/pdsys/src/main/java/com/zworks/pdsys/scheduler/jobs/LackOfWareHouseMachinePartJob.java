package com.zworks.pdsys.scheduler.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.enumClass.NoticeType;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.services.DeviceService;
import com.zworks.pdsys.services.NoticeService;
import com.zworks.pdsys.services.WareHouseMachinePartService;

/**
 * 设备保养计划提醒
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Alias("deviceMaitenaceJob")
@Component
public class LackOfWareHouseMachinePartJob implements Job {
    @Autowired 
    WareHouseMachinePartService wareHouseMachinePartService;
    @Autowired 
    DeviceService deviceService;
    
    @Autowired 
    NoticeService noticeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	//每天凌晨对接下来的7天内需保养的机器进行通知
    	WareHouseMachinePartModel whmp = new WareHouseMachinePartModel();
    	whmp.getFilterCond().put("lackObj", true);
    	List<WareHouseMachinePartModel> ds = wareHouseMachinePartService.queryList(whmp);
    	if(ds == null || ds.size() == 0) {
    		return;
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("下列备件库存不足：<br>" +
    			  "<pre>编号\t必备库存\t当前库存<br>");
    			
		List<NoticeModel> notices = new ArrayList<NoticeModel>();
		for(WareHouseMachinePartModel whmpTmp : ds) {
			MachinePartModel machinePart = whmpTmp.getMachinePart();
			
			if(whmpTmp.getNum() < machinePart.getWareHouseMinNum()) {
				sb.append(machinePart.getPn() + "\t" + machinePart.getWareHouseMinNum() + "\t" + whmpTmp.getNum() + "<br>");
			}
		}
		sb.append("</pre>");
		
		NoticeModel s = new NoticeModel();
		s.setContent(sb.toString());
		s.setType(NoticeType.LackOfWareHouseMachinePartJob.ordinal());
		s.setRefId(0);
		
		s.setSender(new UserModel());

		//@todo 暂时从当前的设备维护员中取一名作为接受者
		List<DeviceModel> list = deviceService.queryList(new DeviceModel());
		int userId = 0;
		for(DeviceModel d : list) {
			userId = d.getUser().getId();
			break;
		}
		UserModel receiver = new UserModel();
		receiver.setId(userId);
		s.setReceiver(receiver);
		notices.add(s);
		noticeService.add(s);
    }
}
