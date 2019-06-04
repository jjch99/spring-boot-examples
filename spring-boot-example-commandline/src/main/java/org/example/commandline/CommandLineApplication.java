package org.example.commandline;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(CommandLineApplication.class, args);
    }

    @Override
    public void run(String...args) {

        if (args != null) {
            log.info("args: " + Arrays.asList(args));
        }

        Integer x = jdbcTemplate.queryForObject("select 1", Integer.class);
        log.info("x = " + x);

    }

}
