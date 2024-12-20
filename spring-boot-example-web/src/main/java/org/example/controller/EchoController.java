package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
public class EchoController {

    @RequestMapping(value = "/echo", method = { RequestMethod.GET, RequestMethod.POST })
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

        return body;
    }

}
