package org.example;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SwaggerExampleApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SwaggerExampleApplication.class);
        application.setBannerMode(Mode.OFF);
        application.run(args);
    }

}
