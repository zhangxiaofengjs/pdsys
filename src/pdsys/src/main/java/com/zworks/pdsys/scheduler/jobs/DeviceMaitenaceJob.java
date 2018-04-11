package com.zworks.pdsys.scheduler.jobs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.zworks.pdsys.services.DeviceService;

/**
 * 设备保养计划提醒
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Alias("deviceMaitenaceJob")
public class DeviceMaitenaceJob implements Job {
    @Autowired 
    DeviceService deviceService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	JobDataMap data = context.getMergedJobDataMap();
    	//deviceService.add(null);
//    	ss.dowork();
    	System.out.println(data.get("PARAM_Q") + DateFormat.getTimeInstance(DateFormat.FULL).format(new Date()));
    }
}
