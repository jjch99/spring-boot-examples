package org.example.quartz.cluster.task;

import org.example.quartz.cluster.service.HelloService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloTask implements Job {

    @Autowired
    private HelloService helloService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Starting job");
        try {
            helloService.hello();
            Thread.sleep(5000L);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("Finishing job");
    }

}
