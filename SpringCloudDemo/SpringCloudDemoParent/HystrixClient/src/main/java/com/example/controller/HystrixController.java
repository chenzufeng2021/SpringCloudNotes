package com.example.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class HystrixController {

    /**
     * 通过localhost:8080/FirstHystrix?id=xx的方式传递Id
     * HystrixCommand：指定熔断时快速返回方法；
     *                             该方法参数列表、返回值需与HystrixCommand修饰的方法一致
     *                             默认先执行自定义的备选处理
     * @return String
     */
    @GetMapping("FirstHystrix")
    @HystrixCommand(fallbackMethod = "testFallBack", defaultFallback = "defaultFallback")
    public String invokeFirstHystrix(Integer id) {
        log.info("============================");
        log.info("First Hystrix Demo !");
        if (id < 0) {
            throw new RuntimeException("无效Id！");
        }
        log.info("============================");
        return "First Hystrix demo is done !";
    }

    /**
     * 自定义备选处理
     * 一旦熔断后，不再运行throw new RuntimeException("无效Id！")，而运行testFallBack
     * @param id id
     * @return String
     */
    public String testFallBack(Integer id) {
        return "当前服务繁忙，请稍后再试！不合法Id：" + id;
    }

    /**
     * 使用Hystrix默认的备选处理
     */
    public String defaultFallback() {
        return "执行Hystrix默认的备选处理";
    }
}
