package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.HelloRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${hello.target:Tom}")
    private String target;

    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HelloRequest request) {
        log.info("hello");
        return "Hello " + target;
    }

    @GetMapping("/exception")
    public String exception() {
        log.info("exception");
        throw new RuntimeException();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        log.info("exceptionHandler");
        return "exception";
    }

}
