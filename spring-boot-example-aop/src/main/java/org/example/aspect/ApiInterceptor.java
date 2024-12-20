package org.example.aspect;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    @Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
    public void apiMethodAspect() {

    }

    @Around("apiMethodAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Gson gson;
        if (log.isDebugEnabled()) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        } else {
            gson = new Gson();
        }

        Object[] args = joinPoint.getArgs();
        List argList = new ArrayList(args.length);
        for (Object arg : args) {
            if (arg != null && arg.getClass().getName().startsWith("javax.servlet.")) {
                argList.add(arg.toString());
            } else {
                argList.add(arg);
            }
        }
        log.info("invoke {}, args: {}", joinPoint.getSignature(), gson.toJson(argList));

        try {
            Object ret = joinPoint.proceed();

            log.info("ret: {}", gson.toJson(ret));

            return ret;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

}
