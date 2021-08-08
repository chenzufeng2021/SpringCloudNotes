package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage HystrixServiceApplication
 * @EnableCircuitBreaker 开启 Hystrix 的断路器功能
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class HystrixServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixServiceApplication.class, args);
    }
}
