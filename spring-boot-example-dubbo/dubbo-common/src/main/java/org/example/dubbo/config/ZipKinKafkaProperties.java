package org.example.dubbo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "zipkin.tracing.kafka")
public class ZipKinKafkaProperties {

    private String bootstrapServers;

    private String topic;

    private Integer messageMaxBytes;

    private HashMap<String, String> overrides;

}
