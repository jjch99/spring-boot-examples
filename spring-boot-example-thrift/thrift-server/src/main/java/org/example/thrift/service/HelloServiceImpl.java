package org.example.thrift.service;

import org.apache.thrift.TException;
import org.example.HelloService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloServiceImpl implements HelloService.Iface {

    @Override
    public String hello(String name) throws TException {
        log.info(name);
        return "hello " + name;
    }

}
