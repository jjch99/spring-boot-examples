package org.example.config.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloTask implements Runnable {

    @Scheduled(fixedRate = 5000L)
    @Override
    public void run() {
        log.info("Hello");
    }

}
