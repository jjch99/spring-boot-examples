package org.example;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaTest {

    @Test
    public void sslSendMessage() throws Exception {

        log.info(InetAddress.getLocalHost().getCanonicalHostName());

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String userHome = System.getProperty("user.home");
        props.put("security.protocol", "SSL");
        props.put("ssl.truststore.location", userHome + "/kafka.certificate/client.truststore.jks");
        props.put("ssl.truststore.password", "123456");
        props.put("ssl.keystore.location", userHome + "/kafka.certificate/client.keystore.jks");
        props.put("ssl.keystore.password", "123456");

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String uuid = UUID.randomUUID().toString();
        String topic = "test";
        String key = uuid;
        String val = String.format("%s-%s", key, time);

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>(topic, key, val));
        }
        producer.close();

    }

    @Test
    public void consumerMessage() {

        final Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        String userHome = System.getProperty("user.home");
        props.put("security.protocol", "SSL");
        props.put("ssl.truststore.location", userHome + "/kafka.certificate/client.truststore.jks");
        props.put("ssl.truststore.password", "123456");
        props.put("ssl.keystore.location", userHome + "/kafka.certificate/client.keystore.jks");
        props.put("ssl.keystore.password", "123456");

        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        String topic = "test";
        consumer.subscribe(Collections.singletonList(topic));

        int giveUp = 10;
        int noRecordsCount = 0;
        int count = 0;

        while ((count++) < 1000) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    break;
                } else {
                    continue;
                }
            }

            for (ConsumerRecord<String, String> record : consumerRecords) {
                log.info("Consumer Record:({}, {}, {}, {})", record.key(), record.value(), record.partition(),
                        record.offset());
            }

            consumer.commitAsync();
        }

        consumer.close();
        log.info("DONE");
    }

}
