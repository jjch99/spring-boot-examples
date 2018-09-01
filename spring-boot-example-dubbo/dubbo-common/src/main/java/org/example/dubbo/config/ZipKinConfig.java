package org.example.dubbo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.Tracing;
import brave.http.HttpTracing;
import zipkin2.Span;
import zipkin2.codec.Encoding;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka11.KafkaSender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
public class ZipKinConfig {

    // @Value("${zipkin.tracing.local-service-name:local-service-name}")
    @Value("${spring.application.name}")
    private String localServiceName;

    /**
     * zipkin服务地址
     */
    @Value("${zipkin.tracing.endpoint:http://localhost:9411/api/v2/spans}")
    private String zipkinEndPoint;

    /**
     * Kafka地址
     */
    @Value("${zipkin.tracing.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    /**
     * Kafka Topic
     */
    @Value("${zipkin.tracing.kafka.topic}")
    private String kafkaTopic;

    @Autowired
    private Sender sender;

    @Bean
    @ConditionalOnClass(KafkaSender.class)
    @ConditionalOnProperty(name = "zipkin.tracing.kafka.bootstrap-servers", havingValue = "")
    public KafkaSender kafkaSender() {
        KafkaSender sender = KafkaSender.newBuilder()
                .bootstrapServers(kafkaBootstrapServers)
                .topic(kafkaTopic)
                .encoding(Encoding.JSON)
                .build();
        return sender;
    }

    @Bean
    @ConditionalOnMissingBean(KafkaSender.class)
    public Sender okHttpSender() {
        Sender sender = OkHttpSender
                .newBuilder()
                .endpoint(zipkinEndPoint)
                .encoding(Encoding.JSON)
                .build();
        return sender;
    }

    @Bean
    public Reporter<Span> reporter() {
        return AsyncReporter.builder(sender).build();
    }

    @Bean
    public Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName(localServiceName)
                .spanReporter(reporter())
                .build();
    }

    @Bean
    public HttpTracing httpTracing() {
        HttpTracing httpTracing = HttpTracing.create(tracing());
        return httpTracing;
    }

}
