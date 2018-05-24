package com.zworks.pdsys.scheduler.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.enumClass.NoticeType;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.DeviceService;
import com.zworks.pdsys.services.NoticeService;

/**
 * 设备保养计划提醒
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Alias("deviceMaitenaceJob")
@Component
public class DeviceMaitenaceJob implements Job {
    @Autowired 
    DeviceService deviceService = new DeviceService();
    
    @Autowired 
    NoticeService noticeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	//每天凌晨对接下来的7天内需保养的机器进行通知
    	DeviceModel d = new DeviceModel();
    	d.getFilterCond().put("toMaitenaceInDays", 6);
    	d.setMaitenacedDate(new Date());
    	List<DeviceModel> ds = deviceService.queryList(d);
    	if(ds == null || ds.size() == 0) {
    		return;
    	}
    	
		List<NoticeModel> notices = new ArrayList<NoticeModel>();
		for(DeviceModel device : ds) {
			NoticeModel n = new NoticeModel();
			n.setRefId(device.getId());
			n.getFilterCond().put("start", DateUtils.getCurrentDate());
			if(noticeService.exist(n)) {
				continue;
			}
			
			MachineModel m = device.getMachine();
			String content = String.format("编号：%s<br> 机器：%s %s<br> 保养预定：%s",
								device.getNo(),
								m.getPn(), m.getName(),
								DateUtils.format(DateUtils.addDay(device.getMaitenacedDate(),  (int)m.getMaitenacePeriod())));
			
			NoticeModel s = new NoticeModel();
			s.setContent(content);
			s.setType(NoticeType.DeviceMaitenaceJob.ordinal());
			s.setRefId(device.getId());
			
			s.setSender(new UserModel());
			
			UserModel receiver = new UserModel();
			receiver.setId(device.getUser().getId());
			s.setReceiver(receiver);
			notices.add(s);
			noticeService.add(s);
		}
    }
}
