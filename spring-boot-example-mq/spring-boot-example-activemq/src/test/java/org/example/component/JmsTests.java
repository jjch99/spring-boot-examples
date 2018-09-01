package org.example.component;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.example.common.Queues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTests {

    @Autowired
    private Sender sender;

    @Test
    public void contextLoads() throws InterruptedException {
        Destination destination = new ActiveMQQueue(Queues.TEST);
        for (int i = 0; i < 100; i++) {
            sender.sendMessage(destination, "Hello world");
            sender.sendDelayMessage(destination, "Delayed Message");
        }
    }

}
