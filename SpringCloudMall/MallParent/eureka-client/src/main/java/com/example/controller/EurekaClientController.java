package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage EurekaClientController
 */
@RestController
@RequestMapping("/Client")
public class EurekaClientController {
    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private String servicePort;

    @GetMapping("/info")
    public String info() {
        return "Info from service: " + serviceName + ", port: " + servicePort + " !";
    }
}
