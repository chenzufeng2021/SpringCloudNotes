package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage EurekaSecurityServerApplication
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaSecurityServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSecurityServerApplication.class, args);
    }
}
