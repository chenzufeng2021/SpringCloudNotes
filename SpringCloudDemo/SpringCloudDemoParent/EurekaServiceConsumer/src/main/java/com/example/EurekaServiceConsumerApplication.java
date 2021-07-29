package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chenzufeng
 * @date 2021/7/30
 * @usage EurekaServiceConsumerApplication 服务消费者
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceConsumerApplication.class, args);
    }
}
