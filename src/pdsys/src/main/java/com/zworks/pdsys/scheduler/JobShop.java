package com.zworks.pdsys.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.scheduler.jobs.DataBaseBackupJob;
import com.zworks.pdsys.scheduler.jobs.DeviceMaitenaceJob;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Service
public class JobShop {
	 @Autowired
	 private Scheduler scheduler;
	
	public void initialize() {
		//备份数据库
		createJob(DataBaseBackupJob.class,
				  "pdsys",
				  "0 0 1 * * ?",//每天1:00执行
				  "数据库备份计划Job",
				  "数据库备份计划JobTrigger");
		
		//设备维护提醒
		createJob(DeviceMaitenaceJob.class,
				  "pdsys",
				  //"0/30 * * * * ? *",//每天00:00执行
				  "0 0 23 1/1 * ? *",//每天23:00执行
				  "设备维护计划Job",
				  "设备维护计划JobTrigger");
	}
	
	private void createJob(Class<? extends Job> jobClass, String jobGroup, String cronExpression, String jobDescription, String triggerDescription) {
		try {
			String jobName = jobClass.getName();
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(jobDescription).build();
			
			//create a trigger
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(triggerDescription).withSchedule(schedBuilder).build();
			
			//scheduled!!
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			System.err.println(e);
		}
	}
}
