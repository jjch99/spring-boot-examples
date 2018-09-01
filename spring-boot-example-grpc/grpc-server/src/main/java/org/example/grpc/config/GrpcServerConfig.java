package org.example.grpc.config;

import org.example.grpc.DemoServiceGrpc;
import org.example.grpc.GrpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import zipkin2.codec.Encoding;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
public class GrpcServerConfig {

    @Autowired
    private DemoServiceGrpc.DemoServiceImplBase demoService;

    @Value("${grpc.port}")
    private int port = 9090;

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

    @Bean(initMethod = "start", destroyMethod = "stop")
    public GrpcServer grpcServer(Reporter reporter) {
        GrpcServer grpcServer = new GrpcServer(port, reporter);
        grpcServer.addService("demoService", demoService);
        return grpcServer;
    }

}
