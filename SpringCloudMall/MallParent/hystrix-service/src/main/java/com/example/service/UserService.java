package com.example.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.example.entity.CommonResult;
import com.example.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage UserService
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    /**
     * 1. 添加调用方法与服务降级方法
     */

    @HystrixCommand(fallbackMethod = "getDefaultUser")
    public CommonResult getUser(Long id) {
        // getForObject(String url, Class<T> responseType, Object... uriVariables)
        return restTemplate.getForObject(userServiceUrl + "/User/{1}", CommonResult.class, id);
        // return restTemplate.getForObject(userServiceUrl + "/User/{"+ id +"}", CommonResult.class, id);
    }

    public CommonResult getDefaultUser(@PathVariable Long id) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    /**
     * 2.  设置命令、分组及线程池名称
     */

    @HystrixCommand(
            fallbackMethod = "getDefaultUser",
            commandKey = "getUserCommand",
            groupKey = "getUserGroup",
            threadPoolKey = "getUserThreadPool"
    )
    public CommonResult getUserCommand(@PathVariable Long id) {
        log.info("getUserCommand id: {}", id);
        return restTemplate.getForObject(userServiceUrl + "/User/{1}", CommonResult.class, id);
    }

    /**
     * 3.  使用 ignoreExceptions 忽略某些异常降级
     * 这里忽略了 NullPointerException
     */

    @HystrixCommand(
            fallbackMethod = "getDefaultUser2",
            ignoreExceptions = {NullPointerException.class}
    )
    public CommonResult getUserException(Long id) {
        if (id == 1) {
            throw new IndexOutOfBoundsException();
        } else if (id == 2) {
            throw new NullPointerException();
        }
        return restTemplate.getForObject(userServiceUrl + "/User/{1}", CommonResult.class, id);
    }

    public CommonResult getDefaultUser2(@PathVariable Long id, Throwable throwable) {
        log.info("getDefaultUser2 id: {}, throwable class: {}", id, throwable.getClass());
        User defaultUser2 = new User(-2L, "defaultUser2", "123456");
        return new CommonResult<>(defaultUser2);
    }

    /**
     * 4. 使用缓存
     */

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCache")
    public CommonResult getUserCache(Long id) {
        log.info("getUserCache id: {}", id);
        return restTemplate.getForObject(userServiceUrl + "/User/{1}", CommonResult.class, id);
    }

    /**
     * 为缓存生成 key
     */
    public String getCacheKey(Long id) {
        return String.valueOf(id);
    }

    /**
     * 5. 删除缓存
     */
    @CacheRemove(commandKey = "getUserCache", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public CommonResult removeCache(Long id) {
        log.info("removeCache id: {}", id);
        return restTemplate.postForObject(userServiceUrl + "/User/Delete/{1}", null, CommonResult.class, id);
    }

    /**
     * 6. 请求合并
     */
    @HystrixCollapser(
            batchMethod = "getUserByIds",
            collapserProperties = {
                    @HystrixProperty(
                            name = "timerDelayInMilliseconds",
                            value = "100"
                    )
            }
    )
    public Future<User> getUserFuture(Long id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                CommonResult commonResult = restTemplate.getForObject(userServiceUrl + "/User/{1}", CommonResult.class, id);
                Map data = (Map) commonResult.getData();
                User user = BeanUtil.mapToBean(data, User.class, true);
                log.info("getUserById userName: {}", user.getUserName());
                return user;
            }
        };
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Long> ids) {
        log.info("getUserByIds: {}", ids);
        CommonResult commonResult = restTemplate.getForObject(
                userServiceUrl + "/User/GetUserByIds?ids={1}",
                CommonResult.class,
                CollUtil.join(ids, ","));
        return (List<User>) commonResult.getData();
    }
}
