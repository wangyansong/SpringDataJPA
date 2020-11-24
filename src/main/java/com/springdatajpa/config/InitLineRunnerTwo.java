package com.springdatajpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/10/19
 */
@Component
@Order(value = 2)
public class InitLineRunnerTwo implements ApplicationRunner {

    @Autowired
    private ServerProperties serverProperties;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        int port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
//        System.out.println(String.format("swagger-ui:http://localhost:%d%s/swagger-ui.html",port, contextPath));
        System.out.println(String.format("Application has been started..."));

    }
}
