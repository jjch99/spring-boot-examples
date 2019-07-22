package org.example.component;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

    /**
     * 注意 {@link KafkaListener#group()} 与 {@link Consumer#groupId} 并非一码事
     * 
     * @param record
     */
    @KafkaListener(topics = { "test" })
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("record = {}", record);
            log.info("message = {}", message);
        }
    }

}
