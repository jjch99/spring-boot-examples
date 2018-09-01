package org.example.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DemoSimpleJob implements SimpleJob {

    @Value("${hello:hello}")
    private String hello;

    @Override
    public void execute(ShardingContext shardingContext) {

        log.info("job名称:" + shardingContext.getJobName()
                + ",分片数量:" + shardingContext.getShardingTotalCount()
                + ",当前分区:" + shardingContext.getShardingItem()
                + ",当前分区名称:" + shardingContext.getShardingParameter()
                + ",当前自定义参数:" + shardingContext.getJobParameter());

        log.info(hello);

    }

}
