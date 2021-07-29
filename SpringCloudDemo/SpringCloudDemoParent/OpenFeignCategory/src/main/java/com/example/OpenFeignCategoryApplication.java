package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenzufeng
 */

@SpringBootApplication
// 开启服务注册
@EnableDiscoveryClient
// 开启OpenFeogn客户端调用
@EnableFeignClients
public class OpenFeignCategoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignCategoryApplication.class, args);
    }
}
