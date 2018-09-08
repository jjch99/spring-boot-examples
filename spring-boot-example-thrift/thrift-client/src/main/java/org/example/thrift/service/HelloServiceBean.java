package org.example.thrift.service;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.HelloService;
import org.example.thrift.config.ThriftServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloServiceBean implements HelloService.Iface {

    @Autowired
    private ThriftServiceConfig serviceConfig;

    @Override
    public String hello(String name) {

        TTransport transport = null;
        try {
            // TTransport、TProtocol 都需要与服务端一致
            transport = new TSocket(serviceConfig.getHost(), serviceConfig.getPort(), serviceConfig.getTimeout());
            // transport = new org.apache.thrift.transport.TFramedTransport(transport);
            transport.open();
            TProtocol protocol = new TCompactProtocol(transport);
            HelloService.Client client = new HelloService.Client(protocol);
            return client.hello(name);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

}
