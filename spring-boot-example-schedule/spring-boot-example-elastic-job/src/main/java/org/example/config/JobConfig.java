package org.example.config;

import org.example.job.DemoSimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JobConfig {

    @Autowired
    private CoordinatorRegistryCenter registryCenter;

    @Bean(initMethod = "init")
    public JobScheduler demoJobScheduler(DemoSimpleJob demoSimpleJob) {

        String jobName = demoSimpleJob.getClass().getSimpleName(); // 任务名称
        String cron = "0/10 * * * * ?";
        int shardingNum = 2; // 分片数量

        log.info(String.format("reg job '%s', sharding number: %d", jobName, shardingNum));

        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingNum)
                .shardingItemParameters("0=北京,1=上海").build();
        SimpleJobConfiguration simpleJobConfiguration =
                new SimpleJobConfiguration(coreConfiguration, demoSimpleJob.getClass().getName());
        LiteJobConfiguration jobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();

        SpringJobScheduler jobScheduler =
                new SpringJobScheduler(demoSimpleJob, registryCenter, jobConfiguration);
        return jobScheduler;
    }

}
