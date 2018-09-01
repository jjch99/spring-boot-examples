package org.example.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class MyListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ServletContext Destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("ServletContext Initialized");
        logger.info(sce.getServletContext().getServerInfo());
    }

}
