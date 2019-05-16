package org.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableScheduling
@EnableApolloConfig
@SpringBootApplication
public class ApolloLog4j2ExampleApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApolloLog4j2ExampleApplication.class);
        application.setBannerMode(Mode.OFF);
        application.run(args);
    }

    @Scheduled(fixedRate = 3000L)
    public void log() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("hello");
    }

}
