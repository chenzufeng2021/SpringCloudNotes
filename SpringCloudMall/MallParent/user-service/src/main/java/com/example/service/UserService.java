package com.example.service;

import com.example.entity.User;

import java.util.List;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage UserService
 */
public interface UserService {
    void create(User user);
    User getUser(Long id);
    void update(User user);
    void delete(Long id);
    User getByUserName(String userName);
    List<User> getUserByIds(List<Long> ids);
}
