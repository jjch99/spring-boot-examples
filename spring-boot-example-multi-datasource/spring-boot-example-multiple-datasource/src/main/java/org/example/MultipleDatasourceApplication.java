package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// @ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MultipleDatasourceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MultipleDatasourceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MultipleDatasourceApplication.class);
        // application.setBannerMode(Mode.OFF);
        application.run(args);
    }

}
