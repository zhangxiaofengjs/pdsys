package com.zworks.pdsys.common.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 使用hibernate的注解来进行验证
 * @author ZHAI
 * @date 2018-04-17 10:50
 */
public class ValidatorUtils {
	private static Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
				//Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 * @param object 待校验对象
	 * @throws RRException 校验不通过，则报PdsysException异常
	 */
	public static <T> JSONResponse doValidate(Object object) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		if (constraintViolations.size()>0) {
			StringBuilder msg = new StringBuilder();
			msg.append(constraintViolations.iterator().next().getMessage());
			return JSONResponse.error(msg.toString());
		}
		return null;
	}
}
