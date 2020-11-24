package com.springdatajpa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/7/22
 */
@Order(value = 1)
@Component
public class InitLineRunnerOne implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitLineRunnerOne.class);

    public void run(String... args) throws Exception {

        LOGGER.info("======= InitLineRunnerOne ========");

    }

}
