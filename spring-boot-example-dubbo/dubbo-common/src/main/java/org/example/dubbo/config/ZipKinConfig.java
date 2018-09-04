package org.example.dubbo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Value("${spring.application.name}")
    private String localServiceName;

    @Value("${zipkin.tracing.endpoint}")
    private String zipkinEndPoint;

    @Autowired(required = false)
    private ZipKinKafkaProperties kafkaProperties;

    @Autowired
    private Sender sender;

    @Bean
    @ConditionalOnClass(KafkaSender.class)
    @ConditionalOnBean(ZipKinKafkaProperties.class)
    public KafkaSender kafkaSender() {

        KafkaSender.Builder builder = KafkaSender.newBuilder()
                .bootstrapServers(kafkaProperties.getBootstrapServers())
                .topic(kafkaProperties.getTopic())
                .encoding(Encoding.JSON);
        if (kafkaProperties.getMessageMaxBytes() != null){
            builder.messageMaxBytes(kafkaProperties.getMessageMaxBytes());
        }
        if (kafkaProperties.getOverrides() != null) {
            builder.overrides(kafkaProperties.getOverrides());
        }

        KafkaSender sender = builder.build();
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
