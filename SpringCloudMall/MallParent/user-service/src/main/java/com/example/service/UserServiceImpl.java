package com.example.service;

import com.example.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService{
    private List<User> userList;

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "chen", "123"));
        userList.add(new User(2L, "zufeng", "123456"));
        userList.add(new User(3L, "chenzufeng", "123456"));
    }

    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(Long id) {
        List<User> userCollect = userList.stream().filter(user -> user.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userCollect)) {
            return userCollect.get(0);
        }
        return null;
    }

    @Override
    public void update(User user) {
        userList.stream().filter(userItem -> userItem.getId().equals(user.getId())).forEach(userItem -> {
            userItem.setUserName(user.getUserName());
            userItem.setPassword(user.getPassword());
        });
    }

    @Override
    public void delete(Long id) {
        User user = getUser(id);
        if (user != null) {
            userList.remove(user);
        }
    }

    @Override
    public User getByUserName(String userName) {
        List<User> userCollect = userList.stream().filter(user -> user.getUserName().equals(userName)).collect(Collectors.toList());
        if (! CollectionUtils.isEmpty(userCollect)) {
            return userCollect.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        return userList.stream().filter(user -> ids.contains(user.getId())).collect(Collectors.toList());
    }
}
