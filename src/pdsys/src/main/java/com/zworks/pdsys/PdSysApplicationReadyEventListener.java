package com.zworks.pdsys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.mappers.DeviceMapper;
import com.zworks.pdsys.scheduler.config.JobShop;
import com.zworks.pdsys.services.CustomerService;

@Component
public class PdSysApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	JobShop jobShop;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		//！！！！！！为什么不能自动注入JobShop???!!!!暂时先用下面的加强注入
		event.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
		
		//初始化batch
		jobShop.initialize();
	}
}

