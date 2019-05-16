package org.example.utils;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

/**
 * @see org.springframework.boot.logging.LoggingApplicationListener
 * @see org.springframework.boot.context.config.ConfigFileApplicationListener
 */
@Slf4j
public class MyApplicationListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationListener: " + event.getClass().getName());
    }

}
