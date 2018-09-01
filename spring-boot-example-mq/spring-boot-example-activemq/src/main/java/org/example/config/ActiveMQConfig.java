package org.example.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.DiscardingDeadLetterStrategy;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 启动一个本地ActiveMQ服务
 */
@Configuration
public class ActiveMQConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean(destroyMethod = "stop")
    @ConditionalOnProperty(name = "start.activemq.embedded.broker", havingValue = "true")
    public BrokerService broker() {

        if (!StringUtils.startsWith(brokerUrl, "tcp://localhost:")) {
            throw new IllegalArgumentException("spring.activemq.broker-url=" + brokerUrl);
        }

        logger.info("Start embedded ActiveMQ broker ...");

        String bindAddress = brokerUrl;
        BrokerService broker = new BrokerService();
        try {
            broker.addConnector(bindAddress);
            // 支持延迟消息、定时消息
            broker.setSchedulerSupport(true);
            broker.setUseJmx(false);
            broker.setPersistent(false);

            TempUsage tempUsage = new TempUsage();
            tempUsage.setLimit(128 * 1024 * 1024);
            SystemUsage systemUsage = new SystemUsage();
            systemUsage.setTempUsage(tempUsage);
            broker.setSystemUsage(systemUsage);

            DeadLetterStrategy deadLetterStrategy = new DiscardingDeadLetterStrategy();
            PolicyEntry policyEntry = new PolicyEntry();
            policyEntry.setDeadLetterStrategy(deadLetterStrategy);
            PolicyMap policyMap = new PolicyMap();
            policyMap.setDefaultEntry(policyEntry);
            broker.setDestinationPolicy(policyMap);

            broker.start();

        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

        return broker;
    }

}
