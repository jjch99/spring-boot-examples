package org.example.dubbo;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

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
        props.put("ssl.truststore.location", userHome + "/kafka-jks/kafka-client-ts.jks");
        props.put("ssl.truststore.password", "client1234567");
        props.put("ssl.keystore.location", userHome + "/kafka-jks/kafka-client-ks.jks");
        props.put("ssl.keystore.password", "client1234567");
        props.put("ssl.key.password", "client1234567");

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String uuid = UUID.randomUUID().toString();
        String topic = "test";
        String key = uuid;
        String val = String.format("%s %s value", time, uuid);

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord<String, String>(topic, key, val));
        producer.close();

    }

}
