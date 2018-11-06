package org.example.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import com.ctrip.framework.apollo.spring.config.PropertySourcesConstants;

/**
 * 初始化apollo需要的一些参数
 * 
 * @see com.ctrip.framework.apollo.spring.boot.ApolloApplicationContextInitializer
 * @see com.ctrip.framework.apollo.internals.DefaultMetaServerProvider
 * @see com.ctrip.framework.foundation.internals.provider.DefaultApplicationProvider
 * @see com.ctrip.framework.foundation.internals.provider.DefaultServerProvider
 */
public class ApolloConfigurator implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        System.setProperty(PropertySourcesConstants.APOLLO_BOOTSTRAP_ENABLED, Boolean.TRUE.toString());

        // 如果VM参数和环境变量里没有特殊指定，则将程序配置中的相关参数设置进去

        if (System.getProperty("app.id") == null && environment.getProperty("apollo.app.id") != null) {
            System.setProperty("app.id", environment.getProperty("apollo.app.id"));
        }

        if (System.getProperty("env") == null && System.getenv("ENV") == null
                && environment.getProperty("apollo.env") != null) {
            System.setProperty("env", environment.getProperty("apollo.env"));
        }

        if (System.getProperty("idc") == null && System.getenv("IDC") == null
                && environment.getProperty("apollo.idc") != null) {
            System.setProperty("idc", environment.getProperty("apollo.idc"));
        }

    }

}
