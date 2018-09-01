
package org.example.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        log.info(name);
        return "hello " + name;
    }

}
