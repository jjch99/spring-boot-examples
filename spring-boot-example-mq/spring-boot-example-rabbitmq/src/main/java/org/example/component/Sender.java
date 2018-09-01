package org.example.component;

import java.util.Date;

import org.example.config.QueueConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String msg = "hello " + new Date();
        log.info("Sender : " + msg);
        this.rabbitTemplate.convertAndSend(QueueConfig.HELLO, msg);
    }

}
