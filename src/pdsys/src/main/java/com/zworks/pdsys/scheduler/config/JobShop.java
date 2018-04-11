package com.zworks.pdsys.scheduler.config;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
		createJob(DeviceMaitenaceJob.class.getName(),
				  "PDSYS",
				  "0/10 * * * * ?",
				  "设备维护计划Job",
				  "设备维护计划JobTrigger");
	}
	
	private void createJob(String jobName, String jobGroup, String cronExpression, String jobDescription, String triggerDescription) {
		try {
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
			@SuppressWarnings("unchecked")
			Class<? extends Job> clazz = (Class<? extends Job>)Class.forName(jobName);
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
			
			//create a trigger
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(triggerDescription).withSchedule(schedBuilder).build();
			
			//scheduled!!
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException | ClassNotFoundException e) {
			System.err.println(e);
		}
	}
}
