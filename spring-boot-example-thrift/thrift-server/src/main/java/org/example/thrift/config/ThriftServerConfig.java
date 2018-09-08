package org.example.thrift.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.example.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ThriftServerConfig {

    @Value("${thrift.server.port:25308}")
    private int port;

    @Autowired
    private HelloService.Iface helloService;

    @Bean(destroyMethod = "shutdown")
    public ExecutorService thriftServerExecutor() {
        return Executors.newFixedThreadPool(4);
    }

    @Bean(destroyMethod = "stop")
    @ConditionalOnProperty(name = "thrift.server.type", havingValue = "simple")
    public TServer thriftSimpleServer() throws Exception {

        TProcessor tprocessor = new HelloService.Processor(helloService);
        TServerSocket serverTransport = new TServerSocket(port);
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(new TCompactProtocol.Factory());
        // tArgs.protocolFactory(new TBinaryProtocol.Factory());
        // tArgs.protocolFactory(new TJSONProtocol.Factory());
        final TServer server = new TSimpleServer(tArgs);
        thriftServerExecutor().execute(new Runnable() {
            @Override
            public void run() {
                log.info("Thrift TSimpleServer binding on port {}", port);
                server.serve();
            }
        });
        return server;

    }

    @Bean(destroyMethod = "stop")
    @ConditionalOnMissingBean(TServer.class)
    public TServer thriftNonblockingServer() throws Exception {

        TProcessor processor = new HelloService.Processor(helloService);

        // TMultiplexedProcessor multiplexedProcessor = new TMultiplexedProcessor();
        // multiplexedProcessor.registerProcessor(helloService.getClass().getName(), processor);

        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport);
        args.processor(processor);
        args.protocolFactory(new TCompactProtocol.Factory());
        args.transportFactory(new TFramedTransport.Factory());
        args.processorFactory(new TProcessorFactory(processor));
        args.selectorThreads(2);

        // 工作线程池，非I/O线程
        ExecutorService pool = Executors.newFixedThreadPool(200);
        args.executorService(pool);

        final TThreadedSelectorServer server = new TThreadedSelectorServer(args);
        thriftServerExecutor().execute(new Runnable() {
            @Override
            public void run() {
                log.info("Thrift TThreadedSelectorServer binding on port {}", port);
                server.serve();
            }
        });
        return server;

    }

}
