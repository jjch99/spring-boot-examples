package org.example.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TestNGApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNGApplication.class, args);
    }

    @Service
    public class HelloService {

        public String hello(String name) {
            return "Hello " + name;
        }

    }

    @RestController
    public class HelloController {

        @Autowired
        private HelloService helloService;

        @GetMapping("/hello")
        public String hello(String name) {
            return helloService.hello(name);
        }

    }

}
