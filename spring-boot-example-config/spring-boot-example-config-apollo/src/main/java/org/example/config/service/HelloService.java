package org.example.config.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Value("${example.greetings:Hello}")
    private String greetings;

    public String hello(String name) {
        return greetings + " " + name;
    }

}
