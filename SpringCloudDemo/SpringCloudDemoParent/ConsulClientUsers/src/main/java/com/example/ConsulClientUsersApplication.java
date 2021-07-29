package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chenzufeng
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulClientUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulClientUsersApplication.class, args);
    }
}
