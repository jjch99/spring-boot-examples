package org.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public final static String HELLO = "hello";

    @Bean
    public Queue helloQueue() {
        return new Queue(HELLO);
    }

}
