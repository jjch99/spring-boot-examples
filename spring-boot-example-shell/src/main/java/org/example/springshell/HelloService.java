package org.example.springshell;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String hello(String name) {
        return "Hello " + name;
    }

}
