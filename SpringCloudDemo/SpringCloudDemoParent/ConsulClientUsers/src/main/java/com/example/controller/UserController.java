package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class UserController {

    /**
     * 2.1.服务注册与发现客户端对象
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 2.2.具有负载均衡策略的客户端对象
     * import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("UsersServerInvokeOrdersServer")
    public String invokeOrdersServer() {
        log.info("==============================");
        log.info("用户信息服务调用订单服务。。。");
        /*
        // 1.使用restTemplate发起请求，调用商品服务
        RestTemplate restTemplate = new RestTemplate();
        String orderResult = restTemplate.getForObject("http://localhost:9999/FindOrders", String.class);
        return "调用订单服务成功，结果为：" + orderResult;  */

        // 2.使用restTemplate + ribbon进行服务调用

        /*
        // 2.1.使用DiscoveryClient将服务注册中心的信息拉取到本地，但没有实现负载均衡
        List<ServiceInstance> consulClientOrders = discoveryClient.getInstances("ConsulClientOrders");
        for (ServiceInstance consulClientOrder : consulClientOrders) {
            log.info("服务主机：[{}]；服务端口：[{}]；服务地址：[{}]",
                    consulClientOrder.getHost(), consulClientOrder.getPort(), consulClientOrder.getUri());
            log.info("==============================");
        }
        // DiscoveryClient将服务注册中心的信息全部拉取到本地：使用-Dserver.port创建了两个订单服务
        String orderResult = new RestTemplate().getForObject(consulClientOrders.get(0).getUri() + "/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;  */

        /*
        // 2.2.使用LoadBalancerClient进行服务调用——默认策略是轮询
        ServiceInstance consulClientOrder = loadBalancerClient.choose("ConsulClientOrders");
        log.info("服务主机：[{}]；服务端口：[{}]；服务地址：[{}]",
                consulClientOrder.getHost(), consulClientOrder.getPort(), consulClientOrder.getUri());
        String orderResult = new RestTemplate().getForObject(consulClientOrder.getUri() + "/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;  */

        // 2.3.使用@LoadBalanced让当前对象具有ribbon负载均衡特性
        String result = restTemplate.getForObject("http://ConsulClientOrders/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", result);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + result;
    }
}
