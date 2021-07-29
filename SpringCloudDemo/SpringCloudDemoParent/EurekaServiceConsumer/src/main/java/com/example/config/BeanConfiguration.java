package com.example.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenzufeng
 * @date 2021/7/30
 * @usage BeanConfiguration 通过配置 RestTemplate 来调用接口
 * @LoadBalanced 会自动构造 LoadBalancerClient 接口的实现类并注册到 Spring 容器中
 */
@Configuration
public class BeanConfiguration {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
