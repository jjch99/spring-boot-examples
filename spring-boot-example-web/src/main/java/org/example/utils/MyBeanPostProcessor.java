package org.example.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    private boolean postProcessBeforeInitializationPrinted = false;

    private boolean postProcessAfterInitializationPrinted = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!postProcessBeforeInitializationPrinted) {
            System.out.println(getClass().getName() + "#postProcessBeforeInitialization");
            postProcessBeforeInitializationPrinted = true;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!postProcessAfterInitializationPrinted) {
            System.out.println(getClass().getName() + "#postProcessAfterInitialization");
            postProcessAfterInitializationPrinted = true;
        }
        return bean;
    }

}
