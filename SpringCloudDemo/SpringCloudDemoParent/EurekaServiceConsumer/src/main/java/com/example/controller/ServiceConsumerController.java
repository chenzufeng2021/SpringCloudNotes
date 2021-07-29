package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenzufeng
 * @date 2021/7/30
 * @usage ServiceConsumerController 服务消费者控制器
 */
@RestController
public class ServiceConsumerController {
    @Autowired
    private RestTemplate getRestTemplate;

    @RequestMapping("/CallService")
    public String callHello() {
        return getRestTemplate.getForObject("http://EurekaServiceProvider/Service",String.class);
    }
}
