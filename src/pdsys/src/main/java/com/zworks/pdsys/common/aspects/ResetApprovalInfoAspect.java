package com.zworks.pdsys.common.aspects;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.models.ApprovalInfoModel;
import com.zworks.pdsys.models.BaseModel;
import com.zworks.pdsys.services.ApprovalInfoService;

/**
 * 对查询的Model,函数返回值进行批复信息的重新设置切面
 * 一般查询出的Model只查了Id，为了方便才用此切片
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/06/02
 */
@Aspect
@Component
public class ResetApprovalInfoAspect {
	@Autowired
	ApprovalInfoService approvalInfoService;
	
	@Pointcut("@annotation(com.zworks.pdsys.common.annotations.ResetApprovalInfo)")
	public void resetApprovalInfoPointCut() {
	}

	@Around("resetApprovalInfoPointCut()")
	public Object adviceAnnoLog(ProceedingJoinPoint point) throws Throwable {
		Object result = point.proceed();
		if(result instanceof BaseModel) {
			resetApprovalInfo(result);
		} else if(result instanceof List<?>) {
			for(Object obj : (List<?>)result) {
				resetApprovalInfo(obj);
			}
		}
		return result;
	}
	
	private void resetApprovalInfo(Object obj) {
		if(obj instanceof BaseModel) {
			BaseModel m = (BaseModel)obj;
			ApprovalInfoModel approvalInfo = m.getApprovalInfo();
			
			if(approvalInfo != null) {
				approvalInfo = approvalInfoService.queryById(approvalInfo.getId());
				m.setApprovalInfo(approvalInfo);
			}
		}
	}
}
