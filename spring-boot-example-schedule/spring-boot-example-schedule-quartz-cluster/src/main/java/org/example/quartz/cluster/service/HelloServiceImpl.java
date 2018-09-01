package org.example.quartz.cluster.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public void hello() {
        log.info("Hello");
    }

}
