package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("org.example.dao")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ShardingJdbcApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShardingJdbcApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ShardingJdbcApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
