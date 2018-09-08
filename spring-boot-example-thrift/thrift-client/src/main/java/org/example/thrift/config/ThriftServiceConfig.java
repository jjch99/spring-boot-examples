package org.example.thrift.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "thrift.service")
public class ThriftServiceConfig {

    private String host;

    private int port;

    private int timeout;

}
