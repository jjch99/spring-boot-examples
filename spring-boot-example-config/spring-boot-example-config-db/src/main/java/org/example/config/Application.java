package org.example.config;

import org.example.config.db.DatabasePropertiesLoader;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Mode.OFF);
        application.addListeners(new DatabasePropertiesLoader());
        application.run(args);
    }

}
