package org.example.grpc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import zipkin2.reporter.Reporter;

public class GrpcServer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private int port;

    private Server server;

    private ServerBuilder serverBuilder;

    private Reporter reporter;

    public GrpcServer(int port, Reporter reporter) {
        serverBuilder = ServerBuilder.forPort(port);
        this.reporter = reporter;
    }

    public void addService(String serviceName, BindableService service) {
        Tracing tracing = Tracing.newBuilder()
                .localServiceName(serviceName)
                .spanReporter(reporter)
                .build();
        GrpcTracing grpcTracing = GrpcTracing.create(tracing);
        serverBuilder.addService(ServerInterceptors.intercept(service, grpcTracing.newServerInterceptor()));
    }

    public synchronized void start() {
        try {
            if (server == null || server.isTerminated()) {
                server = serverBuilder.build().start();
                logger.info("GRPC Server start at port: {}", server.getPort());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public synchronized void stop() {
        if (server != null && !server.isShutdown()) {
            try {
                server.shutdown();
                server.awaitTermination(20, TimeUnit.SECONDS);
                server.shutdownNow();
            } catch (InterruptedException e) {
            }
        }
    }

}
