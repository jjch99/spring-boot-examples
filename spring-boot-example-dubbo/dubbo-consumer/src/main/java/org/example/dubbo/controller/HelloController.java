package org.example.dubbo.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.dubbo.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @DubboReference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(String name) {
        log.info(name);
        return helloService.hello(name);
    }

}
