package org.example.thrift.controller;

import org.example.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @Autowired
    private HelloService.Iface helloService;

    @RequestMapping("/hello")
    public String hello(String name) throws Exception {
        log.info(name);
        return helloService.hello(name);
    }

}
