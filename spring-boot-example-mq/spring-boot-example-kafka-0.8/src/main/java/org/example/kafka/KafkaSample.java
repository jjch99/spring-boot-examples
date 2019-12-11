package org.example.kafka;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaSample {

    @Autowired
    private KafkaConfig kafkaConfig;

    public void produce() {
        KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(kafkaConfig.getProducer());
        for (int i = 0; i < 10; i++) {
            String msg = UUID.randomUUID().toString();
            log.info("send msg: {}", msg);
            producer.send(new ProducerRecord(kafkaConfig.getTopic(), msg));
        }
        producer.close();
    }

    public void consume() {

        ConsumerConfig consumerConfig = new ConsumerConfig(kafkaConfig.getConsumer());
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(consumerConfig);

        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(kafkaConfig.getTopic(), 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(kafkaConfig.getTopic());

        int count = 0;
        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                byte[] data = it.next().message();
                String str = new String(data, StandardCharsets.UTF_8);
                log.info("recv msg: {}", str);

                if (count++ > 10) {
                    break;
                }
            }
        }

        consumer.shutdown();
    }

}
