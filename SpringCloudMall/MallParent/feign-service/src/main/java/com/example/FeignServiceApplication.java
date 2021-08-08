package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage FeignServiceApplication 在启动类上添加 @EnableFeignClients 注解来启用 Feign 的客户端功能
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignServiceApplication.class, args);
    }
}
