package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import lombok.extern.slf4j.Slf4j;

/**
 * Elastic-Job 公共配置
 */
@Slf4j
@Configuration
public class ElasticJobConfig {

    @Value("${elastic-job.zk-server-list}")
    private String serverList;

    @Value("${elastic-job.namespace}")
    private String namespace;

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter registryCenter() {
        log.info("zk server list: " + serverList);
        log.info("namespace: " + namespace);
        ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(
                new ZookeeperConfiguration(serverList, namespace));
        return regCenter;
    }

}
