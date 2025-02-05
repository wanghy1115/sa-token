package com.why.satoken.aspect;


import cn.dev33.satoken.stp.StpUtil;
import com.why.satoken.aspect.service.LogMethodCall;
import com.why.satoken.entity.bo.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MethodCallLoggerAspect {

    private static final Logger loger = LoggerFactory.getLogger(MethodCallLoggerAspect.class);


    // 定义切入点，匹配所有被 @LogMethodCall 注解标记的方法
    @Pointcut("@annotation(com.why.satoken.aspect.service.LogMethodCall)")
    public void logMethodCall() {}


    @Before("logMethodCall()")
    public void logBefore(JoinPoint joinPoint) {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();


        /**
         * 获取注解中的值
         * getLogMethodCallAnnotation，在下面定义，用于获取注解对象
         */
        LogMethodCall logMethodCall = getLogMethodCallAnnotation(joinPoint);
        String securityLevel = logMethodCall.securityLevel();

        // 获取调用人的信息（使用sa-session获取）
        String username = ((UserMessage)StpUtil.getSession().get("userMessage")).getUsername();

        UserMessage userMessage = (UserMessage) StpUtil.getSession().get("userMessage");
        loger.info("用户：{}, 调用方法：{}, 方法安全等级为：{}", username, methodName, securityLevel);
    }

    // 获取方法上的 LogMethodCall 注解
    private LogMethodCall getLogMethodCallAnnotation(JoinPoint joinPoint) {
        // 获取目标方法
        /**
         * joinPoint.getSignature()获取当前连接点（方法的信息）
         * getDeclaringType获取当前方法所在的类的类型
         * getDeclaringType().getMethod() 在这个类中获取指定，
         * getName()，getParameterTypes()根据名称和参数列表确认获取那个方法
         * getAnnotation获取这个方法上面的注解
         */
        try {
            return joinPoint.getSignature().getDeclaringType()
                    .getMethod(joinPoint.getSignature().getName(),
                            ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes())
                    .getAnnotation(LogMethodCall.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("无法获取 LogMethodCall 注解", e);
        }
    }

}
