package com.why.satoken.aspect.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 该注解作用于方法
@Retention(RetentionPolicy.RUNTIME) // 在运行时可以访问到该注解
public @interface LogMethodCall {
    String securityLevel() default "I";
}
