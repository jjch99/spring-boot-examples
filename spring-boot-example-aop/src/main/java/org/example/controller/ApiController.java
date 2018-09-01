package org.example.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {

    @ApiOperation("API demo")
    @RequestMapping("/api/hello")
    public Object hello(HttpServletRequest request) {

        try {
            log.info("----Parameters----");
            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                log.info(name + ": " + request.getParameter(name));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "";
    }

}
