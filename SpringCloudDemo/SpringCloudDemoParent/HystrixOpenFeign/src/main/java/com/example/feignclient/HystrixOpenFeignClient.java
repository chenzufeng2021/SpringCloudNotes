package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenzufeng
 */
@FeignClient("HystrixClient")
public interface HystrixOpenFeignClient {
    /**
     * 调用HystrixClient/HystrixController.invokeFirstHystrix服务
     * RequestParam("id")：底层数据传递方式Query String
     * @param id id
     * @return String
     */
    @GetMapping("FirstHystrix")
    String invokeFirstHystrix(@RequestParam("id") Integer id);
}
