package org.example.config;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableScheduling
@EnableApolloConfig
@SpringBootApplication
public class ApolloExampleApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApolloExampleApplication.class);
        application.setBannerMode(Mode.OFF);
        application.run(args);
    }

}
