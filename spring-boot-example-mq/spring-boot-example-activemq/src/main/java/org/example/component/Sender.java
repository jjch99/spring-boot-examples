package org.example.component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Sender {

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 发送消息
     * 
     * @param destination
     * @param message
     */
    public void sendMessage(Destination destination, final String message) {
        log.info(message);
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送延迟消息
     * 
     * @param destination
     * @param message
     */
    public void sendDelayMessage(Destination destination, final String message) {
        log.info(message);
        jmsTemplate.convertAndSend(destination, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                long delay = 60 * 1000;
                message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
                return message;
            }
        });
    }

}
