package org.example.component;

import org.example.common.Queues;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

    @JmsListener(destination = Queues.TEST)
    public void receiveMessage(String text) {
        log.info("receive message: {}", text);
    }

}
