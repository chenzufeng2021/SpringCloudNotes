package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chenzufeng
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulClientOrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulClientOrdersApplication.class, args);
    }
}
