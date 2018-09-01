package org.example.grpc.client.controller;

import org.example.grpc.DemoMessage;
import org.example.grpc.DemoServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @Autowired
    private ManagedChannel channel;

    @RequestMapping("/hello")
    public Object hello() {

        DemoServiceGrpc.DemoServiceBlockingStub stub = DemoServiceGrpc.newBlockingStub(channel);

        DemoMessage request = DemoMessage.newBuilder().setField1("GRPC").build();
        DemoMessage response = stub.demo(request);
        log.info(response.toString());

        return response.toString();
    }

}
