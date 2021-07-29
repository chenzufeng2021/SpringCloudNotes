package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenzufeng
 * EnableDiscoveryClient：代表服务注册中心Consul客户端
 * EnableFeignClients：开启OpenFeogn客户端调用
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HystrixOpenFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixOpenFeignApplication.class, args);
    }
}
