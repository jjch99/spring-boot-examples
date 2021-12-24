package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.HelloRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@Slf4j
@RestController
public class HelloController {

    @ApiOperation(value = "")
    @RequestMapping(value = "/hello", method = { RequestMethod.GET, RequestMethod.POST })
    public String hello(@ApiParam HelloRequest request) {
        log.info("hello");
        return "Hello " + request.getName();
    }

}
