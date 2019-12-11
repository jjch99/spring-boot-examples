package org.example.kafka;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Kafka08Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Kafka08Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
