package org.example.dubbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class GreetingController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String hello(String name) {
        String uri = UriComponentsBuilder.fromHttpUrl("http://localhost:18081/hello")
                .queryParam("name", name)
                .build().toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

}
