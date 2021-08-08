package com.example.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.example.entity.CommonResult;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage UserHystrixController
 */
@RestController
@RequestMapping("/User")
public class UserHystrixController {
    @Autowired
    private UserService userService;

    /**
     * 1. 用于测试服务降级的接口
     */
    @GetMapping("/TestFallback/{id}")
    public CommonResult testFallback(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * 2. 测试设置命令、分组及线程池名称
     */
    @GetMapping("/TestCommand/{id}")
    public CommonResult testCommand(@PathVariable Long id) {
        return userService.getUserCommand(id);
    }

    /**
     * 3. 使用 ignoreExceptions 忽略某些异常降级
     */
    @GetMapping("/TestException/{id}")
    public CommonResult testException(@PathVariable Long id) {
        return userService.getUserException(id);
    }

    /**
     * 4. 使用缓存
     */
    @GetMapping("/TestCache/{id}")
    public CommonResult testCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return new CommonResult("操作成功！", 200);
    }

    /**
     * 5. 删除缓存
     */
    @GetMapping("/TestRemoveCache/{id}")
    public CommonResult testRemoveCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.removeCache(id);
        userService.getUserCache(id);
        return new CommonResult("操作成功！", 200);
    }

    /**
     * 6. 请求合并
     */
    @GetMapping("/TestCollapser")
    public CommonResult testCollapser() throws ExecutionException, InterruptedException {
        Future<User> future1 = userService.getUserFuture(1L);
        Future<User> future2 = userService.getUserFuture(2L);
        future1.get();
        future2.get();
        ThreadUtil.safeSleep(200);
        Future<User> future3 = userService.getUserFuture(3L);
        future3.get();
        return new CommonResult("操作成功！", 200);
    }
}
