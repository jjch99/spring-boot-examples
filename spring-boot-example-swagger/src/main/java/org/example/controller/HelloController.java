package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.HelloRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hello Controller", description = "Hello Controller swagger")
@Slf4j
@RestController
public class HelloController {

    @Operation(summary = "Hello API", description = "Hello API DOC")
    @RequestMapping(value = "/hello", method = { RequestMethod.GET, RequestMethod.POST })
    public String hello(@Parameter(description = "") HelloRequest request) {
        log.info("hello");
        return "Hello " + request.getName();
    }

}
