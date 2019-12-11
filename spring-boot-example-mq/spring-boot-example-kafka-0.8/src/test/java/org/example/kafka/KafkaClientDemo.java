package org.example.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaClientDemo {

    private static String KAFKA_TOPIC_A = "topic_a";

    private static String KAFKA_TOPIC_B = "topic_b";

    private static String ZK_HOSTS = "localhost:2181";

    private static String BROKERS = "localhost:9092";

    /**
     * @see {@link ConsumerConfig}
     */
    private static kafka.consumer.ConsumerConfig buildConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", ZK_HOSTS);
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        props.put("group.id", "group_demo");
        props.put("client.id", UUID.randomUUID().toString());
        return new kafka.consumer.ConsumerConfig(props);
    }

    /**
     * @see {@link ProducerConfig}
     */
    private static Properties buildProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BROKERS);
        props.put("key.serializer", ByteArraySerializer.class.getName());
        props.put("value.serializer", ByteArraySerializer.class.getName());
        props.put("acks", String.valueOf(1));
        props.put("client.id", UUID.randomUUID().toString());
        return props;
    }

    public static void main(String[] args) throws Exception {

        KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(buildProducerConfig());

        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(KAFKA_TOPIC_A, 1);

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(buildConsumerConfig());

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(KAFKA_TOPIC_A);

        int count = 0;
        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                byte[] data = it.next().message();
                System.out.println("send message to topic: " + KAFKA_TOPIC_B);
                producer.send(new ProducerRecord<>(KAFKA_TOPIC_B, data));

                if (count++ > 200) {
                    return;
                }
            }
        }

        consumer.shutdown();
        producer.close();

    }

}
