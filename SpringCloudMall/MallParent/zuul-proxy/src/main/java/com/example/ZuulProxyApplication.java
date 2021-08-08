package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage ZuulProxyApplication 启动类
 * @EnableZuulProxy 启用 Zuul 的 API 网关功能
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }
}
