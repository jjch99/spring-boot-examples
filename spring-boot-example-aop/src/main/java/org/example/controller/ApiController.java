package org.example.controller;

import java.util.Enumeration;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {

    @Operation(summary = "Hello", description = "")
    @RequestMapping("/api/hello")
    public Object hello(HttpServletRequest request) {

        try {
            log.info("----Parameters----");
            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                log.info(name + ": " + request.getParameter(name));
            }

            log.info("----headers----");
            Enumeration headers = request.getHeaderNames();
            while (headers.hasMoreElements()) {
                String name = (String) headers.nextElement();
                log.info(name + ": " + request.getHeader(name));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "Hello";
    }

    @RequestMapping("/api/greeting")
    public Object greeting(String s) {
        return "OK";
    }

    @RequestMapping("/api/error")
    public Object error(String s) {
        throw new RuntimeException("error");
    }

}
