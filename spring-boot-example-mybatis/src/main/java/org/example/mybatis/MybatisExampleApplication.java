package org.example.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.mybatis.dao")
@SpringBootApplication
public class MybatisExampleApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MybatisExampleApplication.class);
        // application.setBannerMode(Mode.OFF);
        application.run(args);
    }

}
