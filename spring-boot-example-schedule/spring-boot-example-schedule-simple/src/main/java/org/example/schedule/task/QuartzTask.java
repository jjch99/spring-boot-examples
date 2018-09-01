package org.example.schedule.task;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QuartzTask {

    public void hello() {
        log.info("Hello world");
    }

}
