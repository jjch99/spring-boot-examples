package org.example.component;

import org.example.config.QueueConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

    @RabbitListener(queues = QueueConfig.HELLO)
    @RabbitHandler
    public void process(String msg) {
        log.info("Receive  : " + msg);
    }

}
