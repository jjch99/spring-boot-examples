package org.example.grpc.client;

import org.example.grpc.DemoMessage;
import org.example.grpc.DemoServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DemoSimpleClient {

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        DemoServiceGrpc.DemoServiceBlockingStub stub = DemoServiceGrpc.newBlockingStub(channel);

        DemoMessage request = DemoMessage.newBuilder().setField1("GRPC").build();
        DemoMessage response = stub.demo(request);

        System.out.println(response);

        channel.shutdown();
    }

}
