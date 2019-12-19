package org.example.init;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    @Qualifier("clientProperties")
    private Properties clientProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info(clientProperties.toString());
    }

}
