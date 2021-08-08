package com.example.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage FeignConfig 使 Feign打印最详细的HTTP请求日志信息
 */
@Configuration
public class FeignConfig {
    @Bean
    // feign.Logger
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
