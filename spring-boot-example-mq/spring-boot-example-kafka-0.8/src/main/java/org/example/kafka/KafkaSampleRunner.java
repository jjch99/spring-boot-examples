package org.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaSampleRunner implements CommandLineRunner {

    @Autowired
    private KafkaSample kafkaSample;

    @Override
    public void run(String... args) throws Exception {
        kafkaSample.produce();
        kafkaSample.consume();
    }

}
