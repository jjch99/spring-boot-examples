package org.example.kafka;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

    private String topic;

    private Properties consumer = new Properties();

    private Properties producer = new Properties();

}
