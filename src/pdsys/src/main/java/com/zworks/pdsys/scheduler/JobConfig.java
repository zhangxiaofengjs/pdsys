package com.zworks.pdsys.scheduler;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Configuration
public class JobConfig {
	@Autowired
    private JobFactory jobFactory;

	@Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        factory.setOverwriteExistingJobs(true);

        // 延时启动
        factory.setStartupDelay(5);

        // 加载quartz数据源配置
        factory.setQuartzProperties(quartzProperties());

        factory.setAutoStartup(true);
        
        // 自定义Job Factory，用于Spring注入
        factory.setJobFactory(jobFactory);

        return factory;
    }

    /**
     * 加载quartz数据源配置
     * 
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("pdsys.quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
