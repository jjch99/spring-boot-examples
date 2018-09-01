package org.example.grpc.service;

import org.example.grpc.DemoMessage;
import org.example.grpc.DemoServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.grpc.stub.StreamObserver;

@Service
public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void demo(DemoMessage request, StreamObserver<DemoMessage> responseObserver) {
        logger.info(request.toString());
        DemoMessage response = DemoMessage.newBuilder()
                .setField1("Hello")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
