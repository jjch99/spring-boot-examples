package org.example.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Environment就绪后的处理，日志就是在该阶段初始化的（高优先级），一般功能的建议放在日志初始化完成之后
 */
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println(getClass().getName());
    }

}
