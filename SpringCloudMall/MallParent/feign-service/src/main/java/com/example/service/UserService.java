package com.example.service;

import com.example.entity.CommonResult;
import com.example.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage UserService 通过`@FeignClient`注解实现了一个 Feign 客户端，
 * 其中的 value 为 user-service ，表示这是对 user-service 服务的接口调用客户端
 *
 * @FeignClient(value = "user-service")
 * 设置服务降级处理类为 UserFallbackService
 */
@FeignClient(value = "user-service", fallback = UserFallbackService.class)
public interface UserService {
    @PostMapping("/User/Create")
    CommonResult create(@RequestBody User user);

    @GetMapping("/User/{id}")
    CommonResult<User> getUser(@PathVariable Long id);

    @GetMapping("/User/GetByUserName")
    CommonResult<User> getByUserName(@RequestParam String userName);

    @PostMapping("/User/Update")
    CommonResult update(@RequestBody User user);

    @PostMapping("/User/Delete/{id}")
    CommonResult delete(@PathVariable Long id);
}
