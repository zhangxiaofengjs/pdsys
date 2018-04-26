package com.zworks.pdsys.scheduler;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 对产生的Job对象再一次autowire
 * 用于对Job中Service类进行注入
 * 原因：JobShop中产生的JOB都是从类名new出来的，好像不是spring管理自动生成的对象，所以不能注入，
 * 不过考虑，Job上如果@Component后，由springboot的context取出job对象说不定就ok了，有待验证
 * 
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/11
 */
@Component
public class JobFactory extends AdaptableJobFactory {
    
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
