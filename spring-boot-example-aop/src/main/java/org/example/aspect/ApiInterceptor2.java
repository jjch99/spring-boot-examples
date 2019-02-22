package org.example.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ApiInterceptor2 {

    /**
     * 匹配所有: execution(* *(..))
     * <p>
     * 匹配指定类的所有公共方法: execution(public * org.example.service.UserService.*(..))
     * <p>
     * 匹配指定包的所有类的公共方法: execution(public * org.example.service..*.*(..))
     */
    @Pointcut("execution(public * org.example.controller..*.*(..))")
    public void methodAspect() {

    }

    /**
     * 方法执行前运行
     */
    @Before("methodAspect()")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("The method " + methodName + " begins with " + Arrays.asList(args));
    }

    /**
     * 进入方法后执行(先于方法内代码)
     */
    @After("methodAspect()")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("The method " + methodName + " begins with " + Arrays.asList(args));
    }

    /**
     * 正常返回时执行
     */
    @AfterReturning(value = "methodAspect()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("The method " + methodName + " return with " + result);
    }

    /**
     * 出现异常时执行
     */
    @AfterThrowing(value = "methodAspect()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("The method " + methodName + " occurs exception", ex);
    }

}
