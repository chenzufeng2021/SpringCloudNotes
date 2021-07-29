package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class OrderController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("FindOrders")
    public Map<String, Object> findOrders() {
        log.info("==============================");
        log.info("查询所有订单，调用成功！当前服务端口：[{}]", port);
        log.info("==============================");
        Map<String, Object> map = new HashMap<>();
        map.put("message", "服务调用成功，服务提供端口为：" + port);
        map.put("state", true);
        return map;
    }
}
