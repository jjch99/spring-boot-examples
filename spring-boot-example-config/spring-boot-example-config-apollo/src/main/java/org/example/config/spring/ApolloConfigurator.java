package org.example.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import com.ctrip.framework.apollo.spring.config.PropertySourcesConstants;

/**
 * 初始化apollo需要的一些参数
 * 
 * @see com.ctrip.framework.apollo.spring.boot.ApolloApplicationContextInitializer
 */
public class ApolloConfigurator implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        System.setProperty(PropertySourcesConstants.APOLLO_BOOTSTRAP_ENABLED, Boolean.TRUE.toString());

        if (environment.getProperty("apollo.app.id") != null) {
            System.setProperty("app.id", environment.getProperty("apollo.app.id"));
        }

        if (environment.getProperty("apollo.env") != null) {
            System.setProperty("env", environment.getProperty("apollo.env"));
        }

        if (environment.getProperty("apollo.idc") != null) {
            System.setProperty("idc", environment.getProperty("apollo.idc"));
        }

    }

}
