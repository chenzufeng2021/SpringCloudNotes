package com.example.controller;

import com.example.feignclient.HystrixOpenFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzufeng
 */
@RestController
@Slf4j
public class HystrixOpenfeignController {
    @Autowired
    private HystrixOpenFeignClient hystrixOpenFeignClient;

    @GetMapping("HystrixOpenfeign")
    public String testHystrixOpenfeign() {
        log.info("调用HystrixClient/HystrixController.invokeFirstHystrix方法");
        String invokeFirstHystrix = hystrixOpenFeignClient.invokeFirstHystrix(-1);
        log.info("HystrixClient/HystrixController.invokeFirstHystrix方法结果：{}", invokeFirstHystrix);
        return "调用HystrixClient/HystrixController.invokeFirstHystrix方法";
    }
}
