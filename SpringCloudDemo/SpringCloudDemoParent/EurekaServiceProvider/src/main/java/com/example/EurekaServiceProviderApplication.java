package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chenzufeng
 * @date 2021/7/30
 * @usage EurekaServiceProviderApplication 服务提供者
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaServiceProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceProviderApplication.class, args);
    }
}
