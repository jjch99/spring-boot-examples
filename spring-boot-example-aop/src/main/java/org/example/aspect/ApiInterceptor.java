package org.example.aspect;

import java.util.Arrays;
import java.util.Objects;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ApiInterceptor {

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void apiMethodAspect() {

    }

    @Around("apiMethodAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        log.info("arg num: " + args.length + ", args: " + Arrays.asList(args));

        Object ret = null;
        try {
            ret = joinPoint.proceed();

            Gson gson = new Gson();
            log.info("ret: {}", gson.toJson(ret));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // 此处如果屏蔽了异常不抛出去，Controller里的@ExceptionHandler就收不到异常了
            throw e;
        }

        return ret;
    }

}
