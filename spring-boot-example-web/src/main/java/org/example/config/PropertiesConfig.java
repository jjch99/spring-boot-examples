package org.example.config;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Bean(name = "clientProperties")
    @ConfigurationProperties(prefix = "client")
    public Properties clientProperties() {
        return new Properties();
    }

}
