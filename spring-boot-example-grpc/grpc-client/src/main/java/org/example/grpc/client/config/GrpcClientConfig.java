package org.example.grpc.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import zipkin2.codec.Encoding;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.host:localhost}")
    private String grpcHost;

    @Value("${grpc.port}")
    private int grpcPort;

    @Value("${zipkin.endpoint}")
    private String zipkinEndpoint;

    @Bean
    public Sender okHttpSender() {
        Sender sender = OkHttpSender.newBuilder()
                .encoding(Encoding.JSON)
                .endpoint(zipkinEndpoint)
                .connectTimeout(1000)
                .writeTimeout(1000)
                .build();
        return sender;
    }

    @Bean
    public Reporter reporter(Sender sender) {
        return AsyncReporter.create(sender);
    }

    @Bean
    public ManagedChannel managedChannel(Reporter reporter) {
        Tracing tracing = Tracing.newBuilder()
                .localServiceName("GrpcClient")
                .spanReporter(reporter)
                .build();
        GrpcTracing grpcTracing = GrpcTracing.create(tracing);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .intercept(grpcTracing.newClientInterceptor())
                .usePlaintext()
                .build();
        return channel;
    }

}
