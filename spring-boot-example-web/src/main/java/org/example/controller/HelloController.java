package org.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${hello.target:Tom}")
    private String target;

    @RequestMapping("/")
    public String index() {
        log.info("hello");
        return "Hello " + target;
    }

    @RequestMapping("/exception")
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
