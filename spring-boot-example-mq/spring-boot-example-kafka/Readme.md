## kafka 0.8.2 使用

maven 依赖

```
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>0.8.2.1</version>
</dependency>
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka_2.10</artifactId>
    <version>0.8.2.1</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
        </exclusion>
        <exclusion>
            <artifactId>slf4j-log4j12</artifactId>
            <groupId>org.slf4j</groupId>
        </exclusion>
        <exclusion>
            <artifactId>log4j</artifactId>
            <groupId>log4j</groupId>
        </exclusion>
    </exclusions>
</dependency>
```

示例
> https://github.com/apache/kafka/tree/0.8.2/examples/src/main/java/kafka/examples

```java

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaClientDemo {

    private static String KAFKA_TOPIC = "topic_demo";

    private static ConsumerConfig buildConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "group_demo");

        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        props.put("client.id", UUID.randomUUID().toString());
        return new ConsumerConfig(props);
    }

    private static Properties buildProducerConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, String.valueOf(1));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        return props;
    }

    public static void main(String[] args) throws Exception {

        KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(buildProducerConfig());

        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(KAFKA_TOPIC, 1);

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(buildConsumerConfig());

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(KAFKA_TOPIC);

        int count = 0;
        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                byte[] data = it.next().message();
                System.out.println("send to localhost kafka");
                producer.send(new ProducerRecord<>(KAFKA_TOPIC, data));

                if (count++ > 200) {
                    return;
                }
            }
        }

    }

}

```