package com.example.controller;

import com.example.entity.CommonResult;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage UserFeignController
 */
@RestController
@RequestMapping("/User")
public class UserFeignController {
    @Autowired
    private UserService userService;

    @PostMapping("/Create")
    public CommonResult create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public CommonResult<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/GetByUserName")
    public CommonResult<User> getByUserName(@RequestParam String userName) {
        return userService.getByUserName(userName);
    }

    @PostMapping("/Update")
    public CommonResult update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/Delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
