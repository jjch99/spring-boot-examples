package org.example.grpc.client;

import java.util.concurrent.TimeUnit;

import org.example.grpc.DemoMessage;
import org.example.grpc.DemoServiceGrpc;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import zipkin2.codec.Encoding;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class DemoClient {

    public static void main(String[] args) throws InterruptedException {

        String zipkinEndpoint = "http://localhost:9411/api/v2/spans";
        Sender sender = OkHttpSender.newBuilder().encoding(Encoding.JSON).endpoint(zipkinEndpoint)
                .connectTimeout(1000).writeTimeout(1000).build();
        AsyncReporter reporter = AsyncReporter.create(sender);
        Tracing tracing = Tracing.newBuilder().localServiceName("demoServiceClient").spanReporter(reporter).build();
        GrpcTracing grpcTracing = GrpcTracing.create(tracing);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .intercept(grpcTracing.newClientInterceptor())
                .usePlaintext()
                .build();

        DemoServiceGrpc.DemoServiceBlockingStub stub = DemoServiceGrpc.newBlockingStub(channel);

        DemoMessage request = DemoMessage.newBuilder().setField1("GRPC").build();
        DemoMessage response = stub.demo(request);

        System.out.println(response);

        channel.awaitTermination(15, TimeUnit.SECONDS);
    }

}
