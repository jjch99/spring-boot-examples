package org.example.component;

import java.util.Date;
import java.util.UUID;

import org.example.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/kafka/send")
    public void send() throws Exception {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());

        String messageString = objectMapper.writeValueAsString(message);
        log.info("message = {}", messageString);
        kafkaTemplate.send("test", messageString);
    }

}
