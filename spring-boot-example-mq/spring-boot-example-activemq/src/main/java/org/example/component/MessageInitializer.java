package org.example.component;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.example.common.Queues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageInitializer implements InitializingBean {

    @Autowired
    private Sender sender;

    @Override
    public void afterPropertiesSet() throws Exception {
        Destination destination = new ActiveMQQueue(Queues.TEST);
        for (int i = 0; i < 100; i++) {
            sender.sendDelayMessage(destination, "Delayed Message");
        }
    }

}
