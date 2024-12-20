
package org.example.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        log.info(name);
        return "hello " + name;
    }

}
