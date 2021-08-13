# Sentinel 实现熔断与限流

## Sentinel 简介

随着微服务的流行，服务和服务之间的稳定性变得越来越重要。 Sentinel 以流量为切入点，从==流量控制、熔断降级、系统负载保护==等多个维度保护服务的稳定性。

Sentinel 具有如下特性:

- 丰富的应用场景：承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀，可以实时熔断下游不可用应用；
- 完备的实时监控：同时提供实时的监控功能。可以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况；
- 广泛的开源生态：提供开箱即用的与其它开源框架/库的整合模块，例如与 Spring Cloud、Dubbo、gRPC 的整合；
- 完善的 SPI 扩展点：提供简单易用、完善的 SPI 扩展点。您可以通过实现扩展点，快速的定制逻辑。

## 安装 Sentinel 控制台

Sentinel 控制台是一个轻量级的控制台应用，它可用于实时查看单机资源监控及集群资源汇总，并提供了一系列的规则管理功能，如流控规则、降级规则、热点规则等。

下载地址：[https://github.com/alibaba/Sentinel/releases](https://github.com/alibaba/Sentinel/releases)



下载完成后，在其所在文件夹打开cmd，在命令行输入如下命令运行 Sentinel 控制台：

```bash
D:\Winsoftware\Sentinel>java -jar sentinel-dashboard-1.8.2.jar
```



Sentinel 控制台默认运行在 8080 端口上，登录账号密码均为`sentinel`，通过如下地址可以进行访问：[http://localhost:8080](http://localhost:8080/)



# 创建 sentinel-service 模块

## 创建项目、添加依赖

在 pom.xml 中添加相关依赖，这里使用 Nacos 作为注册中心，所以需要同时添加 Nacos 的依赖：

```xml
<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>

    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## 添加配置

```yaml
server:
  port: 8090

spring:
  application:
    name: sentinel-service
  cloud:
    nacos:
      discovery:
        # 配置 Nacos 地址
        server-addr: localhost:8848
    sentinel:
      transport:
        # 配置 sentinel dashboard 地址
        dashboard: localhost:8080
        port: 8719

service-url:
  user-service: http://nacos-user-service

management:
  endpoints:
    web:
      exposure:
        include: "*"
```



## 创建入口类

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SentinelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelServiceApplication.class, args);
    }
}
```



## 创建实体类

### User

```java
package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String userName;
    private String password;
}
```

### CommonResult

```java
package com.example.entity;

public class CommonResult<T> {
    private T data;
    private String message;
    private Integer code;

    public CommonResult() {
    }

    public CommonResult(T data, String message, Integer code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public CommonResult(String message, Integer code) {
        this(null, message, code);
    }

    public CommonResult(T data) {
        this(data, "操作成功", 200);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
```



# 限流功能

Sentinel Starter 默认为所有的 HTTP 服务提供了==限流埋点==，也可以通过使用`@SentinelResource`来==自定义限流行为==。

## 创建 RateLimitController 类

创建 RateLimitController 类用于测试熔断和限流功能：

```java
package com.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.entity.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController {
    /**
     * 按资源名称限流，需要指定限流处理逻辑
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult("按资源名称限流", 200);
    }

    /**
     * 按URL限流，有默认的限流处理逻辑
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl", blockHandler = "handleException")
    public CommonResult byUrl() {
        return new CommonResult("按url限流", 200);
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(exception.getClass().getCanonicalName(), 200);
    }
}
```



## 根据资源名称、URL限流

可以根据 @SentinelResource 注解中定义的 value（资源名称）来进行限流操作，但是需要指定限流处理逻辑。

- 流控规则可以在 Sentinel 控制台进行配置，由于使用了 Nacos 注册中心，先启动 Nacos 和 sentinel-service；

- 由于 Sentinel 采用的==懒加载==规则，==需要先访问下接口，Sentinel 控制台中才会有对应服务信息==。先访问该接口：[http://localhost:8090/rateLimit/byResource]()

    ```json
    {
        "data": null,
        "message": "按资源名称限流",
        "code": 200
    }
    ```

    

- 在 Sentinel 控制台配置==流控规则==，根据 @SentinelResource 注解的 value 值：（单机阈值设置为1）

- 快速访问上面的接口，可以发现返回了自己定义的限流处理信息：

    ```json
    {
        "data": null,
        "message": "com.alibaba.csp.sentinel.slots.block.flow.FlowException",
        "code": 200
    }
    ```

    

- 在 Sentinel 控制台配置流控规则，使用访问的 URL：

- 访问接口：[http://localhost:8090/rateLimit/byUrl]()：

    ```json
    # 正常访问
    {
        "data": null,
        "message": "按url限流",
        "code": 200
    }
    
    # 限流访问
    Blocked by Sentinel (flow limiting)
    ```

    



## 自定义限流处理逻辑

可以自定义通用的限流处理逻辑，然后在 @SentinelResource 中指定。

### 创建 CustomBlockHandler

创建`handler#CustomBlockHandler`类用于自定义限流处理逻辑：

```java
package com.example.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.entity.CommonResult;

public class CustomBlockHandler {
    public CommonResult handlerException(BlockException exception) {
        return new CommonResult("自定义限流信息", 200);
    }
}
```



### 修改 RateLimitController

在 RateLimitController 中使用自定义限流处理逻辑：

```java
package com.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.entity.CommonResult;
import com.example.handler.CustomBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController {
    /**
     * 按资源名称限流，需要指定限流处理逻辑
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult("按资源名称限流", 200);
    }

    /**
     * 按URL限流，有默认的限流处理逻辑
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl", blockHandler = "handleException")
    public CommonResult byUrl() {
        return new CommonResult("按url限流", 200);
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(exception.getClass().getCanonicalName(), 200);
    }

    /**
     * 自定义通用的限流处理逻辑
     */
    @GetMapping("/customBlockHandler")
    @SentinelResource(value = "customBlockHandler", blockHandler = "handlerException", blockHandlerClass = CustomBlockHandler.class)
    public CommonResult blockHandler() {
        return new CommonResult("限流成功", 200);
    }
}
```

### 测试

GET：[http://localhost:8090/rateLimit/customBlockHandler]()

```json
{
    "data": null,
    "message": "限流成功",
    "code": 200
}
```

