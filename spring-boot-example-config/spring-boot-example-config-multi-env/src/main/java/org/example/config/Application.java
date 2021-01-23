package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Mode.OFF);
        application.run(args);
    }

    @Value("${key1}")
    private String key1;

    @Override
    public void run(String... strings) {
        System.out.println(key1);
    }

}
