package com.caostudy.ecommerce.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Cao Study
 * @description IgnoreResponseAdvice 注解：用于标识接口不返回统一响应
 * @date 2021/7/22 19:33
 * 可用于类上和方法上，同时注解保持到运行时
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
