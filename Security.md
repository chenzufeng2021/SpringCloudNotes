---
typora-copy-images-to: SpringCloudPictures
---

# Oauth2 使用入门

Spring Cloud Security 为构建安全的 SpringBoot 应用提供了一系列解决方案，结合 Oauth2 可以实现==单点登录、令牌中继、令牌交换==等功能。

# OAuth2 简介

OAuth 2.0 是用于==授权==的行业标准协议。OAuth 2.0 为简化客户端开发提供了特定的授权流，包括 Web 应用、桌面应用、移动端应用等。

- Resource owner（资源拥有者）：拥有该资源的最终用户，他有访问资源的账号密码；
- Resource server（资源服务器）：拥有受保护资源的服务器，如果请求包含正确的访问令牌，可以访问资源；
- Client（客户端）：访问资源的客户端，会使用访问令牌去获取资源服务器的资源，可以是浏览器、移动设备或者服务器；
- Authorization server（认证服务器）：用于认证用户的服务器，如果客户端认证通过，发放访问资源服务器的令牌。

## 四种授权模式

- Authorization Code（授权码模式）：正宗的 OAuth2 的授权模式，==客户端先将用户导向认证服务器==，登录后获取==授权码==，然后进行授权，最后根据授权码获取访问==令牌==；
- Implicit（简化模式）：和授权码模式相比，取消了获取授权码的过程，==直接获取访问令牌==；
- Resource Owner Password Credentials（密码模式）：客户端直接向用户获取用户名和密码，之后向认证服务器获取访问令牌；
- Client Credentials（客户端模式）：客户端直接通过客户端认证（比如 client_id 和 client_secret）从认证服务器获取访问令牌。

### 授权码模式

![授权码模式](SpringCloudPictures/授权码模式.png)

- (A) 客户端将用户导向==认证服务器==；
- (B) 用户在认证服务器进行登录并授权；
- (C) 认证服务器返回授权码给客户端；
- (D) 客户端通过==授权码和跳转地址==向认证服务器获取==访问令牌==；
- (E) 认证服务器发放访问令牌（有需要带上刷新令牌）。

### 密码模式

![密码模式](SpringCloudPictures/密码模式.png)

- (A) 客户端从用户获取用户名和密码；
- (B) 客户端通过用户的用户名和密码访问认证服务器；
- (C) 认证服务器返回访问令牌（有需要带上刷新令牌）。

# Oauth2 的使用

## 创建 oauth2-server 模块

### 创建 oauth2-server 项目、引入依赖

在父项目基础上创建 oauth2-server 项目，并添加相关依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-security</artifactId>
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

### 添加配置、创建启动类

配置文件：

```yaml
server:
  port: 8080
spring:
  application:
    name: oauth2-service
```

启动类：

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth2ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}
```

### 添加实体类 User

```java
package com.example.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public User(String userName, String password, List<GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```

### 添加 SpringSecurity 配置 SecurityConfig

在 config 包中添加 SpringSecurity 配置，允许==认证相关路径的访问==及==表单登录==：

```java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/login/**", "/logout/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
    }
}
```



### 添加 UserService 实现 UserDetailsService 接口

在 service 包中添加 UserService 实现 UserDetailsService 接口，用于==加载用户信息==：

```java
package com.example.service;

import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    /**
     * 是 com.example.entity.User
     * 不是 org.springframework.security.core.userdetails.User
     */
    private List<User> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("chen", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("zufeng", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<User> userListFond = this.userList.stream().filter(user -> user.getUsername().equals(userName)).collect(Collectors.toList());
        if (! CollectionUtils.isEmpty(userListFond)) {
            return userListFond.get(0);
        } else {
            throw  new UsernameNotFoundException("用户名或密码错误！");
        }
    }
}
```

### 添加认证服务器配置 AuthorizationServerConfig

在 config 包中添加 AuthorizationServerConfig 认证服务器配置，使用 @EnableAuthorizationServer 注解开启：

```java
package com.example.config;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager).userDetailsService(userService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置 client_id
                .withClient("admin")
                // 配置 client-secret
                .secret(passwordEncoder.encode("admin123456"))
                // 配置访问 token 的有效期
                .accessTokenValiditySeconds(3600)
                // 配置刷新 token 的有效期
                .refreshTokenValiditySeconds(864000)
                // 配置 redirect_uri，用于授权成功后跳转
                .redirectUris("http://www.baidu.com")
                // 配置申请的权限范围
                .scopes("all")
                // 配置 grant_type，表示授权类型
                .authorizedGrantTypes("authorization_code", "password");
    }
}
```

### 添加资源服务器配置 ResourceServerConfig

添加资源服务器配置，使用 @EnableResourceServer 注解开启：

```java
package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                // 配置需要保护的资源路径
                .antMatchers("/user/**");
    }
}
```

### 添加登录接口

添加需要登录的接口用于测试：

```java
package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/CurrentUser")
    // org.springframework.security.core.Authentication
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }
}
```

## 授权码模式使用

- 启动 oauth2-server 服务；

- 在浏览器访问该地址进行登录授权：[http://localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all&state=normal]()（链接跳转至[http://localhost:8080/login]()）

- 输入账号密码进行登录操作

- 授权后，浏览器会带着授权码跳转到指定的路径：

    ```markdown
    https://www.baidu.com/?code=Ds0VQc&state=normal
    ```

    

- 使用 Postman 请求地址[http://localhost:8080/oauth/token](http://localhost:8080/oauth/token)，输入授权码，获取访问令牌

    - 使用 Basic 认证通过 client_id 和 client_secret 构造一个 Authorization 头信息：

    - 在 Body 中添加以下参数信息，通过 POST 请求获取访问令牌：

        ```markdown
        code: Ds0VQc
        grant_type: authorization_code
        client_id: admin
        redirect_uri: http://www.baidu.com
        scpoe: all
        ```

    - 获得访问令牌

        ```markdown
        {
            "access_token": "017123d8-c75b-4fb1-87c0-6baaeff572c5",
            "token_type": "bearer",
            "expires_in": 3600,
            "scope": "all"
        }
        ```

- 在请求头中添加访问令牌，访问需要登录认证的接口进行测试：[http://localhost:8080/user/GetCurrentUser](http://localhost:8080/user/GetCurrentUser)

    - Headers：

        ```markdown
        Authorization: bearer 017123d8-c75b-4fb1-87c0-6baaeff572c5
        ```

    - 输出：

        ```markdown
        {
            "password": "$2a$10$5T5rv.sK8CJvuGlDAufTeuov7y8WPSZg.JP7TJqs.7dLL0g.3D0Na",
            "authorities": [
                {
                    "authority": "admin"
                }
            ],
            "enabled": true,
            "username": "chen",
            "credentialsNonExpired": true,
            "accountNonLocked": true,
            "accountNonExpired": true
        }
        ```

        

## 密码模式使用

- 启动 oauth2-server 服务；

- 使用 Postman 请求地址[http://localhost:8080/oauth/token](http://localhost:8080/oauth/token)，输入授权码，获取访问令牌：

    - 使用 Basic 认证通过 client_id 和 client_secret 构造一个 Authorization 头信息：

    - 在 Body 中添加以下参数信息，通过 POST 请求获取访问令牌：

        ```markdown
        grant_type: password
        username: chen
        password: 123456
        scpoe: all
        ```

    - 输出

        ```markdown
        {
            "access_token": "0e912dc7-cd0f-40ae-9fba-e052db2d2d37",
            "token_type": "bearer",
            "expires_in": 1653,
            "scope": "all"
        }
        ```

        

