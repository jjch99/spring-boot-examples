package org.example.schedule.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
public class DemoTask {

    private void reportTime(String name) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("report by {}, time: {}", name, df.format(new Date()));
    }

    @Scheduled(fixedRate = 3000)
    public void task1() {
        reportTime("task1");
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    public void task2() {
        reportTime("task2");
    }

    @Scheduled(cron = "15 * 22 * * ?")
    public void task3() {
        reportTime("task3");
    }

}
