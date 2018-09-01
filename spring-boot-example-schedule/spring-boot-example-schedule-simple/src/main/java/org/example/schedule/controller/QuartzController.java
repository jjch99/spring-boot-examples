package org.example.schedule.controller;

import javax.annotation.Resource;

import org.example.schedule.task.QuartzJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class QuartzController {

    @Resource(name = "jobDetail")
    private JobDetail jobDetail;

    @Resource(name = "jobTrigger")
    private CronTrigger cronTrigger;

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @ResponseBody
    @RequestMapping("/quartz/updateTrigerCron")
    public String quartzTest(String cron) throws SchedulerException {
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
        String currentCron = trigger.getCronExpression();
        log.info("当前 trigger cron: {}, 新的 trigger cron: {}", currentCron, cron);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // 按新的cronExpression表达式重新构建trigger
        trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
        trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())
                .withSchedule(scheduleBuilder).build();
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
        return "trigger 已更新";
    }

    @ResponseBody
    @RequestMapping("/quartz/addJob")
    public String addJob(String name, @RequestParam(required = false) String cron) throws Exception {
        if (StringUtils.isEmpty(cron)) {
            cron = "0/5 * * * * ?";
        }
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity("job" + name, "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger" + name, "group1")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return "add job ok";
    }

}
