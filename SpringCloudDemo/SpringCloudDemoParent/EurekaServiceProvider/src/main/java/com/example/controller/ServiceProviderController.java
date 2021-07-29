package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzufeng
 * @date 2021/7/30
 * @usage ServiceProviderController 服务接口
 */
@RestController
public class ServiceProviderController {
    @RequestMapping("Service")
    public String hello() {
        return "服务提供者为您服务。。。";
    }
}
