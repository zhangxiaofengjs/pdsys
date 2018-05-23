package com.zworks.pdsys.scheduler.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.NoticeModel;
import com.zworks.pdsys.services.DeviceService;
import com.zworks.pdsys.services.NoticeService;

/**
 * 设备保养计划提醒
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Alias("deviceMaitenaceJob")
public class DeviceMaitenaceJob implements Job {
    @Autowired 
    DeviceService deviceService;
    
    @Autowired 
    NoticeService noticeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    	String jobName = context.getJobInstance().getClass().getSimpleName();
    	//每天凌晨对当天需维护的有故障的机器进行通知
    	DeviceModel d = new DeviceModel();
    	d.setMaitenacedDate(new Date());
    	List<DeviceModel> ds = deviceService.queryList(d);
    	if(ds!=null && ds.size()>0)
    	{
    		List<NoticeModel> notices = new ArrayList<NoticeModel>();
    		for(int i =0;i<ds.size();i++)
    		{
    			DeviceModel dm = ds.get(i);
    			MachineModel m = dm.getMachine();
    			String comment = "机器["+m.getPn()+m.getName()+"]有故障，需要维修！";
    			NoticeModel s = new NoticeModel();
    			s.setComment(comment);
    			s.setJobName(jobName);
    			notices.add(s);
    		}
    		
    		noticeService.add(notices);
    	}
    }
}
