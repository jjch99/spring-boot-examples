package org.example.controller;

import org.example.domain.User;
import org.example.service.UserServicce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServicce userServicce;

    @GetMapping("/user/{uid}")
    public User getUser(@PathVariable("uid") Long id) {
        return userServicce.get(id);
    }

    @GetMapping("/hello/{hello}")
    public String hello(@PathVariable String hello) {
        return userServicce.hello(hello);
    }

}
