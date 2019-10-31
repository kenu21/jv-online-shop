package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Injector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(InjectInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.info("Dependency injection started...");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            logger.fatal("Inject has failed" + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
