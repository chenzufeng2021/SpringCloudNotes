package com.example.controller;

import com.example.entity.CommonResult;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenzufeng
 * @date 2021/8/7
 * @usage UserRibbonController
 */
@RestController
@Slf4j
@RequestMapping("/User")
public class UserRibbonController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 是 org.springframework.beans.factory.annotation.Value
     * 而非 lombok.Value
     */
    @Value("${service-url.user-service}")
    private String serviceUrl;

    /**
     * http://localhost:8082/User/1
     * @param id id
     * @return CommonResult
     */
    @GetMapping("/{id}")
    public CommonResult getUser(@PathVariable Long id) {
        return restTemplate.getForObject(serviceUrl + "/User/{1}", CommonResult.class, id);
    }

    /**
     * http://localhost:8082/User/GetByUserName?userName="chen"
     * @param userName userName
     * @return CommonResult
     */
    @GetMapping("/GetByUserName")
    public CommonResult getByUserName(@RequestParam String userName) {
        return restTemplate.getForObject(serviceUrl + "/User/GetByUserName?userName={1}", CommonResult.class, userName);
    }

    /**
     * http://localhost:8082/User/GetEntityByUserName?userName="chen"
     * @param userName userName
     * @return CommonResult
     */
    @GetMapping("/GetEntityByUserName")
    public CommonResult getEntityByUserName(@RequestParam String userName) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(serviceUrl
                + "/User/GetByUserName?userName={1}", CommonResult.class, userName);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult("操作失败", 500);
        }
    }

    /**
     * Post: http://localhost:8082/User/Create
     * Body: {"id": 2, "userName": "zufeng", "password": "123"}
     * @param user user
     * @return CommonResult
     */
    @PostMapping("/Create")
    public CommonResult create(@RequestBody User user) {
        return restTemplate.postForObject(serviceUrl + "/User/Create", user, CommonResult.class);
    }

    /**
     * Post: http://localhost:8082/User/Update
     * Body: {"id": 2, "userName": "chenzufeng", "password": "123"}
     * @param user user
     * @return CommonResult
     */
    @PostMapping("/Update")
    public CommonResult update(@RequestBody User user) {
        return restTemplate.postForObject(serviceUrl + "/User/Update", user, CommonResult.class);
    }

    @PostMapping("/Delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        return restTemplate.postForObject(serviceUrl + "/User/Delete/{1}", null, CommonResult.class, id);
    }
}
