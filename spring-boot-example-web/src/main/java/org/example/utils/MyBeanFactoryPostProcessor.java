package org.example.utils;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private Environment env;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(getClass().getName() + "#postProcessBeanFactory");
        System.out.println(Arrays.asList(env.getActiveProfiles()));
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(getClass().getName() + "#setEnvironment");
        this.env = environment;
    }

}
