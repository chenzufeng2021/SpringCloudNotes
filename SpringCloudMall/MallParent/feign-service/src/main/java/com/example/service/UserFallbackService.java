package com.example.service;

import com.example.entity.CommonResult;
import com.example.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage UserFallbackService
 */
@Component
public class UserFallbackService implements UserService {
    @Override
    public CommonResult create(User user) {
        User defaultUser = new User(-1L, "defaultUser", "123");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult<User> getUser(Long id) {
        User defaultUser = new User(-1L, "defaultUser", "123");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult<User> getByUserName(String userName) {
        User defaultUser = new User(-1L, "defaultUser", "123");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult update(User user) {
        return new CommonResult("调用失败，服务被降级！", 500);
    }

    @Override
    public CommonResult delete(Long id) {
        return new CommonResult("调用失败，服务被降级！", 500);
    }
}
