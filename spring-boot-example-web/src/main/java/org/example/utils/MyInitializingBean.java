package org.example.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MyInitializingBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() {
        System.out.println(getClass().getName() + "#afterPropertiesSet");
    }

    @Override
    public void destroy() {
        System.out.println(getClass().getName() + "#destroy");
    }

}
