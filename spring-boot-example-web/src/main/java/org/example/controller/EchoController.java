package org.example.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EchoController {

    @RequestMapping("/echo")
    public String echo(HttpServletRequest request, @RequestBody String body) {

        try {
            log.info("----Parameters----");
            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                log.info(name + ": " + request.getParameter(name));
            }
            log.info("----Body----");
            log.info(body);

            return body;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "echo";
    }

}
