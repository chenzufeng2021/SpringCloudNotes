# 1.什么是微服务

<font color=red>将单体应用基于业务进行拆分，拆分成一个个微小的服务，每一个服务都是一个独立的应用、独立开发、独立部署，独立运行在自己的计算机进程中。对于这些服务，都是进行分布式管理。</font>

- 官网: https://www.martinfowler.com/articles/microservices.html


In short, the microservice architectural style is an approach to developing a single application as `a suite(系列) of small services`, each `running in its own process(进程)` and communicating with lightweight mechanisms, often an HTTP resource API. These services are `built around business capabilities(业务单元)` and `independently deployable(独立部署)` by fully automated deployment machinery. `There is a bare minimum of centralized management(分布式管理) of these services`, which may be written in different programming languages and use different data storage technologies.

```markdown
- a suite of small services                      		                       --基于原来单体应用，开发出一系列微小服务
- running in its own process                                                         --运行在自己的进程里（每个服务运行在自己计算机进程中）
- built around business capabilities                                             --每个服务基于项目中业务进行拆分
- independently deployable                                                          --拆分出来的每个服务独立部署
- bare minimum of centralized management of these services    --这些服务基于分布式管理
```

- 软件服务集群：同一种软件服务的多个服务节点，共同为系统提供服务（厨房里三个厨子做菜）
- 分布式：不同软件集群共同为一个系统提供服务（厨房了有切菜、炒菜、端盘的师傅）
- 官方定义：**微服务就是由一系列围绕自己业务开发的微小服务构成，他们独立部署运行在自己的进程里，基于分布式的管理**
- 通俗定义：**微服务是一种架构，这种架构是将`单个的整体应用程序`分割成更小的项目关联的`独立的服务`。一个服务通常实现一组独立的特性或功能，包含自己的业务逻辑和适配器。各个微服务之间的关联通过暴露api来实现。这些独立的微服务不需要部署在同一个虚拟机，同一个系统和同一个应用服务器中。**

---

# 2.为什么是微服务?

## 单体应用

![单体应用](SpringCloud微服务工具集.assets/单体应用.png)

```markdown
# 1.优点
- 单一架构模式在项目初期很小的时候，开发方便，测试方便，部署方便，运行良好。
# 2.缺点
- 应用随着时间的推进，加入的功能越来越多，最终会变得巨大，一个项目中可能有数百万行代码，互相之间繁琐的jar包依赖。
- 久而久之，开发效率低，代码维护困难
- 还有一个如果想整体应用采用新的技术，新的框架或者语言，那是不可能的。
- 任意模块的漏洞或者错误都会影响这个应用，降低系统的可靠性
```

## 微服务架构应用

![微服务架构](SpringCloud微服务工具集.assets/微服务架构.png)

```markdown
# 1.优点
- 将服务拆分成多个单一职责的小的服务，进行单独部署，服务之间通过网络进行通信
  - 例如 ”商品服务“ 相较于 ”广告服务“ 访问量会更大，因此可以多部署集群节点
- 每个服务应该有自己单独的管理团队，高度自治
- 服务各自有自己单独的职责，服务之间松耦合，避免因一个模块的问题导致服务崩溃

# 2.缺点
- 开发人员要处理分布式系统的复杂性
- 多服务运维难度，随着服务的增加，运维的压力也在增大
- 服务治理 和 服务监控 关键
  - 难点：负载均衡调用、服务雪崩解决（一个服务瘫痪影响其他服务崩溃）、服务配置管理、服务的监控
```



## 架构的演变

```markdown
# 架构的演变过程
- [单一应用架构] `===>` [垂直应用架构] `===>` [分布式服务架构] `===>` [流动计算架构]||[微服务架构] `===>` [未知]
```

- dubbo官网：https://dubbo.apache.org/zh/docs/v2.7/user/preface/background/

![微服务架构演变](SpringCloud微服务工具集.assets/微服务架构演变.png)

```markdown
# 1. All in One Application 	单一架构
- 起初当网站流量很小时，将所有功能都写在一个应用里面，对整个应用进行部署，以减少部署节点和成本。对于这个架构简化增删改查的工作量的数据访问框架（ORM——Object Relation Mappiing）是关键。

# 2. Vertical Application 		垂直架构
- 当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，提升效率的方法之一是将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架（MVC）是关键。

# 3. Distributed Service         分布式服务架构
- 当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的分布式服务框架（RPC——服务间通信手段，提高传输性能——传输层）是关键。

# 4. Elastic Computing		   流动计算架构即微服务架构
- 当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于提高机器利用率的资源调度和治理中心（SOA——面向服务的体系架构）是关键
```

- 友情提醒：**好的架构并不是设计出来的，一定是进化来的！**



# 3.微服务的解决方案

```markdown
# 1.Dubbo (阿里系)
- 初出茅庐：2011年末，阿里巴巴在GitHub上开源了基于Java的分布式服务治理框架Dubbo，之后它成为了国内该类开源项目的佼佼者，许多开发者对其表示青睐。同时，先后有不少公司在实践中基于Dubbo进行分布式系统架构。目前在GitHub上，它的fork、star数均已破万。Dubbo致力于提供高性能和透明化的RPC远程服务调用方案，以及SOA服务治理方案，使得应用可通过高性能[RPC](# 服务间通信方式)实现服务的输出、输入功能和Spring框架无缝集成。Dubbo包含远程通讯、集群容错和自动发现三个核心部分。

- 停止维护：从2012年10月23日Dubbo 2.5.3发布后，在Dubbo开源将满一周年之际，阿里基本停止了对Dubbo的主要升级。只在之后的2013年和2014年更新过2次对Dubbo 2.4的维护版本，然后停止了所有维护工作。Dubbo对Srping的支持也停留在了Spring 2.5.6版本上。

- 死而复生：多年漫长的等待，随着微服务的火热兴起，在国内外开发者对阿里不再升级维护Dubbo的吐槽声中，阿里终于开始重新对Dubbo的升级和维护工作。在2017年9月7日，阿里发布了Dubbo的2.5.4版本，距离上一个版本2.5.3发布已经接近快5年时间了。在随后的几个月中，阿里Dubbo开发团队以差不多每月一版本的速度开始快速升级迭代，修补了Dubbo老版本多年来存在的诸多bug，并对Spring等组件的支持进行了全面升级。

- 2018年1月8日，Dubbo创始人之一梁飞在Dubbo交流群里透露了Dubbo 3.0正在动工的消息。Dubbo 3.0内核与Dubbo 2.0完全不同，但兼容Dubbo 2.0。Dubbo 3.0将以Streaming为内核，不再是Dubbo时代的RPC，但是RPC会在Dubbo 3.0中变成远程Streaming对接的一种可选形态。从Dubbo新版本的路线规划上可以看出，新版本的Dubbo在原有服务治理的功能基础上，将全面拥抱微服务解决方案。

- 结论：当前由于RPC协议、注册中心元数据不匹配等问题，在面临微服务基础框架选型时Dubbo与Spring Cloud是只能二选一，这也是为什么大家总是拿Dubbo和Spring Cloud做对比的原因之一。Dubbo之后会积极寻求适配到Spring Cloud生态，比如作为Spring Cloud的二进制通信方案来发挥Dubbo的性能优势，或者Dubbo通过模块化以及对http的支持适配到Spring Cloud。
```

<img src="SpringCloud微服务工具集.assets/微服务的解决方案.png" alt="微服务的解决方案" style="zoom:50%;" />

```markdown
# 2.Spring Cloud

- Spring Cloud NetFlix  
	基于美国Netflix公司开源的组件进行封装，提供了微服务一栈式的解决方案。
	Spring Netflix 维护、闭源

- Spring Cloud Alibaba
	在Spring Cloud Netflix基础上封装了阿里巴巴的微服务解决方案。
	
- Spring Cloud Spring
	目前Spring官方趋势正在逐渐吸收Netflix组件的精华，并在此基础进行二次封装优化，打造Spring专有的解决方案。
```



# 4.什么是SpringCloud

## 官方定义

- 官方网址: https://cloud.spring.io/spring-cloud-static/Hoxton.SR5/reference/html/

**Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems** (e.g. `configuration management`(配置网络), ` service discovery`(注册中心), `circuit breakers`(服务熔断), `intelligent routing`(网关), `micro-proxy`, `control bus`). Coordination of distributed systems leads to boiler plate patterns, and using Spring Cloud developers can quickly stand up services and applications that implement those patterns. 

```markdown
# 1.翻译
- Springcloud为开发人员提供了在分布式系统中快速构建一些通用模式的工具（例如配置管理、服务发现、断路器、智能路由、微代理、控制总线）。分布式系统的协调导致了锅炉板模式，使用springcloud开发人员可以快速地建立实现这些模式的服务和应用程序。

# 2.通俗理解
- Springcloud是一个含概多个子项目的`开发工具集`，集合了众多的开源框架。它利用了Spring Boot开发的便利性实现了很多功能，如服务注册、服务注册发现、负载均衡等。SpringCloud的出现真正的简化了分布式架构的开发。
- SpringCloud在整合过程中主要是针对Netflix(耐非)开源组件的封装。NetFlix是美国的一个在线视频网站，微服务业的翘楚，它是公认的大规模生产级微服务的杰出实践者，NetFlix的开源组件已经在他大规模分布式微服务环境中经过多年的生产实战验证，因此Spring Cloud中很多组件都是基于NetFlix。
```



## 核心架构及其组件

```markdown
# 1.核心组件说明
- eurekaserver、consul、nacos  	服务注册中心（全局管理服务）组件：记录服务名、IP和端口
- rabbion & openfeign  			  服务负载均衡（将请求均分到集群节点上） 和 服务调用组件
- hystrix & hystrix dashboard         服务断路器（解决某个服务不可用、执行业务逻辑时间过长产生雪崩）  和  服务监控组件
- zuul、gateway 				    服务网关组件（管理微服务统一入口，前端系统访问网关地址，网关进行路由转发）
- config					    统一配置中心组件
- bus                                                消息总线组件：自动配置刷新
......
```

<img src="SpringCloud微服务工具集.assets/微服务核心架构及其组件.png" alt="微服务核心架构及其组件" style="zoom:60%;" />



# 5.环境搭建

## 版本命名

- 官网地址:https://spring.io/projects/spring-cloud

Spring Cloud is an umbrella(伞) project consisting of independent projects with, in principle, different release cadences. To manage the portfolio a BOM (Bill of Materials) is published with a curated set of dependencies on the individual project (see below). The release trains have names, not versions, to avoid confusion with the sub-projects. The names are an alphabetic sequence (so you can sort them chronologically) with names of London Tube stations ("Angel" is the first release, "Brixton" is the second). When point releases of the individual projects accumulate to a critical mass, or if there is a critical bug in one of them that needs to be available to everyone, the release train will push out "service releases" with names ending ".SRX", where "X" is a number.  

```markdown
# 1.翻译
- SpringCloud是一个由众多独立子项目组成的大型综合项目，每个子项目上有不同的发布节奏，都要维护自己发布版本号（子项目使用数字）。
- 为了更好的管理SpringCloud的版本，通过一个资源清单BOM(Bill of Materials)，为避免与子项目的发布号混淆，所以没有采用版本号的方式，而是通过`命名`的方式。这些名字是按字母顺序排列的。如伦敦地铁站的名称（“天使”是第一个版本，“布里斯顿”是第二个版本，"卡姆登"是第三个版本）。当单个项目的点发布累积到一个临界量，或者其中一个项目中有一个关键缺陷需要每个人都可以使用时，发布序列将推出名称以“.SRX”结尾的“服务发布”，其中“X”是一个数字。

# 2.伦敦地铁站名称 [了解]
- Angel、Brixton、Camden、Dalston、Edgware、Finchley、Greenwich、Hoxton

# 最新开始使用数字
- Spring Cloud 2020.0.3
```



## 版本选择

```markdown
# 1.版本选择官方建议 https://spring.io/projects/spring-cloud
- Angel 						版本基于springboot1.2.x版本构建与1.3版本不兼容
- Brixton						版本基于springboot1.3.x版本构建与1.2版本不兼容
	`2017年Brixton and Angel release官方宣布报废`
- Camden      					    版本基于springboot1.4.x版本构建并在1.5版本通过测试
	`2018年Camden release官方宣布报废`
- Dalston、Edgware 				 版本基于springboot1.5.x版本构建目前不能再springboot2.0.x版本中使用
	`Dalston(达尔斯顿)将于2018年12月官方宣布报废。Edgware将遵循Spring Boot 1.5.x的生命周期结束。`
- Finchley 						  版本基于springboot2.0.x版本进行构建,不能兼容1.x版本
- Greenwich					        版本基于springboot2.1.x版本进行构建,不能兼容1.x版本
- Hoxton					       版本基于springboot2.2.x版本进行构建

Spring Cloud Dalston, Edgware, Finchley, and Greenwich have all reached end of life status and are no longer supported.
```

<img src="SpringCloud微服务工具集.assets/版本选择.png" alt="版本选择" style="zoom:50%;" />



## 环境搭建

```markdown
# 说明
- SpringBoot 2.2.5.RELEASE
- SpringCloud Hoxton.SR8
- Java8
- Maven 3.6.3 
- IDEA 2021

# 创建项目
- 创建空项目Empty Project
	D:\Learning\SpringCloud\SpringCloudCode\FirstSpringCloudDemo——SpringBoot 2.5；SpringCloud 2020.3
	D:\Learning\SpringCloud\SpringCloudCode\SpringCloudDemo
- 创建Modules
	选择Maven，不要选择`Create from archetype`，
	地址：D:\Learning\SpringCloud\SpringCloudCode\FirstSpringCloudDemo\SpringCloud_Parent
		  D:\Learning\SpringCloud\SpringCloudCode\SpringCloudDemo\SpringCloudParent
- 删除SpringCloud_Parent中的`src`
- 在pom.xml中进行全局配置
```



```markdown
# 1.在父项目中继承SpringBoot父项目
```

```xml
<!--继承SpringBoot的父项目-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.5.RELEASE</version>
</parent>
```



```markdown
# 2.引入SpringCloud的版本管理
```

```xml
<!--定义SpringCloud使用版本号-->
<properties>
  <java.version>1.8</java.version>
  <spring-cloud.version>Hoxton.SR6</spring-cloud.version>
</properties>

<!--全局管理SpringCloud版本，并不会引入具体依赖-->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```



```markdown
# 3.完成上述操作SpringBoot与SpringCloud环境搭建完成
- 接下来就是使用到具体的SpringCloud组件，在项目中引入具体的组件即可
```



# 6.服务注册中心

## 什么服务注册中心

所谓服务注册中心就是在整个的微服务架构中单独提出一个服务，这个服务`不完成系统的任何的业务功能`，仅仅用来完成对整个微服务系统的`服务注册`和`服务发现`，以及对`服务健康状态`的`监控和管理`功能：

- <font color=red>管理所有服务的健康状态</font>
- <font color=red>存储所有微服务元数据信息</font>

![服务注册中心](SpringCloud微服务工具集.assets/服务注册中心.png)

```markdown
# 1.服务注册中心
- 可以对所有的微服务的信息进行存储，如微服务的名称、IP、端口等
- 可以在进行服务调用时，通过服务发现，查询可用的微服务列表，及网络地址，进行服务调用
- 可以对所有的微服务进行心跳检测，如发现某实例长时间无法访问，就会从服务注册表移除该实例。
```



## 常用的注册中心

SpringCloud支持的多种注册中心Eureka(Netflix)、Consul(Go)、Zookeeper(Java，没有提供UI管理界面)、以及阿里巴巴推出的Nacos。这些注册中心在本质上都是用来管理服务的注册和发现以及服务状态的检查的。

### 1.Eureka

```markdown
# 0.简介
- https://github.com/Netflix/eureka/wiki
- Eureka是Netflix开发的服务发现框架，本身是一个基于REST的服务。SpringCloud将它集成在其子项目spring-cloud-netflix中，以实现SpringCloud的服务注册和发现功能。
- Eureka包含两个组件：Eureka Server(对外)和Eureka Client(对内)。
```

#### 开发Eureka Server

服务注册中心！

`引依赖`$\rightarrow$`写配置`$\rightarrow$`加注解`

```markdown
# 1.创建SpringBoot项目并引入Eureka Server依赖
```

在`SpringCloud_Parent`下创建`Modules`选择Maven，不要选择`Create from archetype`，
地址：D:\Learning\SpringCloud\SpringCloudCode\FirstSpringCloudDemo\SpringCloud_Parent\EurekaServer。

```xml
<!--引入SpringBootWeb-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!--引入 Eureka Server-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```



```markdown
# 2.编写配置application.properties
```

```properties
# 执行服务的端口
server.port=8761
# 指定服务名称，唯一标识
spring.application.name=EurekaServer
# 指定服务注册中心的地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```



```markdown
# 3.开启Eureka Server入口类加入注解：@EnableEurekaServer
```

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```



```markdown
# 4.访问Eureka的服务注册页面
```

[http://localhost:8761](http://localhost:8761)

![访问Eureka的服务注册页面](SpringCloud微服务工具集.assets/访问Eureka的服务注册页面.png)



```markdown
# 5.虽然能看到管理界面为什么项目启动控制台报错?
```

![既当server又当client](SpringCloud微服务工具集.assets/既当server又当client.png)

```markdown
- 出现上述问题原因：既当server又当client。
	eureka组件包含 eurekaserver 和 eurekaclient。server是一个服务注册中心，用来接受客户端的注册。server启动时，会将自己作为一个服务中心启动；与此同时，client的特性会让当前启动的服务，把自己作为eureka的客户端，进行服务中心的注册，默认是启动时立即注册。当项目启动时，服务注册中心还没有创建好，所以找不到服务的客户端组件，就直接报错了。当启动成功服务注册中心创建好了，日后client也能进行注册，就不再报错啦！
- 如果需要其作为一个纯Eureka Server，需要关闭Eureka自己注册自己
```



```markdown
# 6.关闭Eureka自己注册自己
```

```properties
# 执行服务的端口
server.port=8761
# 指定服务名称，唯一标识
spring.application.name=EurekaServer
# 指定服务注册中心的地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# 不再将自己同时作为客户端进行注册
eureka.client.register-with-eureka=false
# 关闭作为客户端时从Eureka Server获取服务信息。让当前服务仅为服务注册中心
eureka.client.fetch-registry=false
```



```markdown
# 7.再次启动，当前应用就是一个单纯Eureka Server，控制器也不再报错
```

![单纯EurekaServer](SpringCloud微服务工具集.assets/单纯EurekaServer.png)



#### 开发Eureka Client

Eureka Client就是日后基于业务差分出来的一个个`微服务`

```markdown
# 1.创建项目并引入Eureka Client依赖
```

继承父项目：在`SpringCloud_Parent`下创建`Modules`选择Maven，不要选择`Create from archetype`，
地址：D:\Learning\SpringCloud\SpringCloudCode\FirstSpringCloudDemo\SpringCloud_Parent\EurekaClient。

```xml
<dependencies>
    <!--引入SpringBootWeb-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--引入Eureka Client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```



```markdown
# 2.编写配置application.properties
```

```properties
# 执行服务的端口
server.port=8888
# 指定服务名称，唯一标识
spring.application.name=EurekaClient
# 指定服务注册中心的地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```



```markdown
# 3.开启Eureka客户端：加入注解
```

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}
```



```markdown
# 4.启动之前的8761的服务注册中心，再启动Eureka客户端服务
# 5.查看Eureka Server的服务注册情况
```

![EurekaServer的服务注册情况](SpringCloud微服务工具集.assets/EurekaServer的服务注册情况.png)



#### Eureka自我保护机制

```markdown
# 0.服务频繁启动时Eureka Server出现错误
- EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.
```

![Eureka自我保护机制](SpringCloud微服务工具集.assets/Eureka自我保护机制.png)

```markdown
# 1.自我保护机制
- 官网地址: https://github.com/Netflix/eureka/wiki/Server-Self-Preservation-Mode
- 默认情况下，如果Eureka Server在一定时间内（默认90秒）没有接收到某个微服务实例的心跳，Eureka Server将会移除该实例。但是`当网络分区故障发生时`，微服务与Eureka Server之间无法正常通信，而微服务本身是正常运行的，此时不应该移除这个微服务，所以引入了自我保护机制。
- Eureka Server在运行期间会去统计心跳失败比例在 15 分钟之内是否低于 85%，如果低于 85%，Eureka Server 会将这些实例保护起来，让这些实例不会过期。这种设计的哲学原理就是"宁可信其有不可信其无！"。
- 自我保护模式正是一种针对网络异常波动的安全保护措施，使用自我保护模式能使Eureka集群更加的健壮、稳定的运行。
```

![Eureka自我保护机制1](SpringCloud微服务工具集.assets/Eureka自我保护机制1.png)

`Chenzf:EurekaClient`端口是`8080`，Eureka Server未立即移除该实例！



```markdown
# 2.在Eureka Server端关闭自我保护机制
```

```properties
eureka.server.enable-self-preservation=false  # 关闭自我保护
eureka.server.eviction-interval-timer-in-ms=3000 # 超时3s自动清除
```



```markdown
# 3.微服务修改减短服务心跳的时间
```

```properties
eureka.instance.lease-expiration-duration-in-seconds=10 # 用来修改Eureka Server默认接受心跳的最大时间，默认是90s
eureka.instance.lease-renewal-interval-in-seconds=5     # 指定客户端多久向Eureka Server发送一次心跳 默认是30s
```



```markdown
# 4.尽管如此关闭自我保护机制还是会出现警告
- THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.
- 官方并不建议在生产情况下关闭
```

![关闭自我保护机制还是会出现警告](SpringCloud微服务工具集.assets/关闭自我保护机制还是会出现警告.png)



#### Eureka停止更新

```markdown
# 1.官方停止更新说明
- https://github.com/Netflix/eureka/wiki
- 在1.x版本项目还是活跃的，但是在2.x版本中停止维护，出现问题后果自负！
```

<img src="SpringCloud微服务工具集.assets/Eureka停止更新.png" alt="image-20200709233215860" style="zoom:40%;" />

#### Eureka Server集群搭建

<img src="SpringCloud微服务工具集.assets/EurekaServer集群搭建.png" alt="image-20210609231844415" style="zoom:50%;" />

节点间彼此发现彼此——相互注册！

![EurekaServer集群搭建1](SpringCloud微服务工具集.assets/EurekaServer集群搭建1.png)

- 方法一：分别搭建三个项目：`8761`、`8762`、`8763`。
- 方法二：`VM options`

```markdown
# 选择Edit Configuration，复制EurekaServerApplication，并进行修改
```

![EurekaServer集群搭建2](SpringCloud微服务工具集.assets/EurekaServer集群搭建2.png)

![EurekaServer集群搭建3](SpringCloud微服务工具集.assets/EurekaServer集群搭建3.png)



```markdown
# 分别修改三个节点中”指定服务注册中心的地址“
```

在`application.properties`（`EurekaServerApplication8761`）中修改：`8761`向`8762`和`8763`注册

```properties
# 执行服务端口
server.port=8761
# 指定服务名称，唯一标识（不要出现下划线）
spring.application.name=EurekaServer
# 指定服务注册中心的地址
eureka.client.service-url.defaultZone=http://localhost:8762/eureka, http://localhost:8763/eureka
```

**立刻启动！**

![EurekaServer集群搭建4](SpringCloud微服务工具集.assets/EurekaServer集群搭建4.png)

在`application.properties`（`EurekaServerApplication8762`）中修改：`8763`向`8761`和`8763`注册

```properties
# 执行服务端口
server.port=8761 # -Dserver.port=8762虚拟了端口（覆盖了原端口8761）
# 指定服务名称，唯一标识（不要出现下划线）
spring.application.name=EurekaServer
# 指定服务注册中心的地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka, http://localhost:8763/eureka
```

**立刻启动！**

![EurekaServer集群搭建5](SpringCloud微服务工具集.assets/EurekaServer集群搭建5.png)

在`application.properties`（`EurekaServerApplication8762`）中修改：`8763`向`8761`和`8763`注册

```properties
# 执行服务端口
server.port=8761 # -Dserver.port=8763虚拟了端口（覆盖了原端口8761）
# 指定服务名称，唯一标识（不要出现下划线）
spring.application.name=EurekaServer
# 指定服务注册中心的地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka, http://localhost:8762/eureka
```

**立刻启动！**

![EurekaServer集群搭建6](SpringCloud微服务工具集.assets/EurekaServer集群搭建6.png)



#### Eureka Server集群验证

运行`EurekaClientApplication`（仅向`8761`注册！）

```properties
server.port=8989
# 服务名称唯一标识
spring.application.name=EurekaClient
# 指定Eureka服务注册中心地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

分别打开http://localhost:8761、http://localhost:8762、http://localhost:8763，均显示：

![EurekaServer集群搭建7](SpringCloud微服务工具集.assets/EurekaServer集群搭建7.png)

保证客户端注册的高可用：

```properties
server.port=8989
# 服务名称唯一标识
spring.application.name=EurekaClient
# 指定Eureka服务注册中心地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka, http://localhost:8762/eureka, http://localhost:8763/eureka
```

启动时，会随机选择一个节点进行注册！



#### Eureka问题

- 最新版本停止更新
- 每次必须手动通过代码形式开发服务注册中心（为SpringBoot项目添加依赖、注释）
  - 约定俗成的内容，一次开发后，不再修改



### 2.Consul

```markdown
# 0.Consul 简介
- https://www.consul.io
- 基于Go语言进行开发的轻量级服务注册中心。
	Consul是一个可以提供服务发现，健康检查，多数据中心，Key/Value存储等功能的分布式服务框架，用于实现分布式系统的服务发现与配置。与其他分布式服务注册与发现的方案，使用起来也较为简单。Consul用Golang实现，因此具有天然可移植性(支持Linux、Windows和Mac OS X)；安装包仅包含一个可执行文件，方便部署。
- 作用
	管理微服务中所有服务注册、发现；管理服务元数据信息存储（服务名、地址）；心跳健康检查
```

#### 安装Consul

安装Consul后，直接将微服务注册到Consul Server即可，不用再开发服务注册中心！

```markdown
# 1.下载Consul
- https://www.consul.io/downloads
```



```markdown
# 2.安装Consul
- 官方安装视频地址: https://learn.hashicorp.com/consul/getting-started/install.html
	解压之后发现consul只有一个脚本文件（Windows中是.exe文件）
```



```markdown
# 3.根据解压缩目录配置环境变量

- 根据安装目录进行环境变量配置 [这里是macos和linux系统配置]
```

![linux配置环境变量](SpringCloud微服务工具集.assets/linux配置环境变量.png)

```markdown
- Win10配置环境变量
```

<img src="SpringCloud微服务工具集.assets/Win10配置环境变量.png" alt="image-20210610100521123" style="zoom:47%;" />



```markdown
# 4.查看Consul环境变量是否配置成功，执行命令出现如下信息代表成功
- consul -v

C:\Users\chenzufeng>consul -v
Consul v1.9.6
Revision bbcbb733b
Protocol 2 spoken by default, understands 2 to 3 (agent will automatically use protocol >2 when speaking to compatible agents)
```



```markdown
# 5.启动Consul服务
- Windows：将cmd切换到consul.exe所在目录
	输入：consul agent -dev
- 配置好环境变量后：
	C:\Users\chenzufeng>consul agent -dev
```



```markdown
D:\WinSoftware\Consul>consul agent -dev
==> Starting Consul agent...
           Version: '1.9.6'
           Node ID: 'bdd87bb5-54be-3c29-272e-edbbf7199b5d'
         Node name: 'Chenzf'
        Datacenter: 'dc1' (Segment: '<all>')
            Server: true (Bootstrap: false)
       Client Addr: [127.0.0.1] (HTTP: 8500, HTTPS: -1, gRPC: 8502, DNS: 8600)
      Cluster Addr: 127.0.0.1 (LAN: 8301, WAN: 8302)
           Encrypt: Gossip: false, TLS-Outgoing: false, TLS-Incoming: false, Auto-Encrypt-TLS: false

==> Log data will now stream in as it occurs:
........
2021-06-10T09:53:24.194+0800 [DEBUG] agent: Skipping remote check since it is managed automatically: check=serfHealth
2021-06-10T09:53:24.194+0800 [DEBUG] agent: Node info in sync
```



```markdown
# 6.访问consul的web服务端口
- http://localhost:8500
	consul默认服务端口是8500
```

![访问consul的web服务](SpringCloud微服务工具集.assets/访问consul的web服务.png)

- dec1：Data Center（数据中心）。可以修改名称：

  ```markdown
  D:\WinSoftware\Consul>consul agent -dev -datacenter=a
  ```

  

- Services：当前Consul服务注册中心服务列表

- Nodes：集群节点



#### 开发Consul客户端即微服务

```markdown
# 1.创建SpringBoot项目并引入Consul客户端依赖
```

```xml
<!--引入SpringBootWeb-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency> 

<!--引入Consul依赖：discovery服务发现、注册-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

<img src="SpringCloud微服务工具集.assets/ConsulDiscovery依赖.png" alt="ConsulDiscovery依赖" style="zoom:50%;" />

```markdown
# 2.编写properties配置，告知Client服务注册中心的信息
```

```properties
server.port=8889 # 可选
spring.application.name=ConsulClient

# 注册Consul服务的主机
spring.cloud.consul.host=localhost
# 注册Consul服务的服务注册中心的端口号
spring.cloud.consul.port=8500
# 指定注册的服务名称，默认就是应用名
spring.cloud.consul.discovery.service-name=${spring.application.name}
```



```markdown
# 3.在入口类上加上注解——开启服务客户端
	通用服务注册客户端注解，代表 Consul Client、Zookeeper Client、Nacos Client
	@EnableDiscoveryClient
```

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chenzufeng
 * @date 2021/06/10
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulClientApplication.class, args);
    }
}
```



```markdown
# 4.启动服务，启动Client，查看Consul界面服务信息
```

![image-20200713135359150](SpringCloud微服务工具集.assets/image-20200713135359150.png)

```markdown
# 5.直接启动Consul Client出现错误：服务不可用
```

<img src="SpringCloud微服务工具集.assets/Consul服务不可用.png" alt="image-20210610105234559" style="zoom:50%;" />

原因：`Consul Server`会检测所有客户端心跳，Client必须给予响应，该服务才能正常使用。而现有客户端没有引入`健康检查依赖`！导致健康检查始终不通过，因此服务不能使用！



#### Consul开启健康监控检查

```markdown
# 1.开启consul健康监控
- 默认情况consul监控健康是开启的，但是必须引入健康监控依赖，才能正确监控健康状态。所以直接启动会显示错误，引入健康监控依赖之后服务正常
```

```xml
<!-- 引入健康监控依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

<img src="SpringCloud微服务工具集.assets/Consul开启健康监控检查.png" alt="Consul开启健康监控检查" style="zoom:47%;" />

没有自我保护机制！（`secure=false`？？）



#### Consul关闭健康监控检查

```properties
# 关闭Consul服务的健康检查[不推荐]
spring.cloud.consul.discovery.register-health-check=false
```



## 不同注册中心区别

```markdown
# 1.CAP定理
- CAP定理：CAP定理又称CAP原则，指的是在一个分布式系统中，一致性（Consistency）、可用性（Availability）、分区容错性（Partition tolerance）。CAP 原则指的是，这三个要素最多只能同时实现两点，不可能三者兼顾。
	一致性（C）：在分布式系统中的所有数据备份，在同一时刻是否同样的值。（等同于所有节点访问同一份最新的数据副本）
	可用性（A）：在集群中一部分节点故障后，集群整体是否还能响应客户端的读写请求。（对数据更新具备高可用性）
	分区容忍性（P），就是高可用性，一个节点崩了，并不影响其它的节点（100个节点，挂了几个，不影响服务，越多机器越好）
	
# 2.Eureka特点
- Eureka中没有使用任何的数据强一致性算法保证不同集群间的Server的数据一致，仅通过数据拷贝的方式争取注册中心数据的最终一致性，虽然放弃数据强一致性但是换来了Server的可用性，降低了注册的代价，提高了集群运行的健壮性。

# 3.Consul特点
- 基于Raft算法，Consul提供强一致性的注册中心服务，但是由于Leader节点承担了所有的处理工作，势必加大了注册和发现的代价，降低了服务的可用性。通过Gossip协议，Consul可以很好地监控Consul集群的运行，同时可以方便通知各类事件，如Leader选择发生、Server地址变更等。

# 4.Zookeeper特点
- 基于Zab协议，Zookeeper可以用于构建具备数据强一致性的服务注册与发现中心，而与此相对地牺牲了服务的可用性和提高了注册需要的时间。
```

![image-20200710135837525](SpringCloud微服务工具集.assets/image-20200710135837525.png)

----

# 7. 服务间通信方式

接下来在整个微服务（基于单体业务，围绕业务进行服务拆分，拆分出来的每一个服务独立应用、独立运行、独立部署，并运行在自己的计算机进程中，基于分布式管理）架构中，我们比较关心的就是`服务间的服务该如何调用`，`有哪些调用方式`？

<img src="SpringCloud微服务工具集.assets/服务间通信方式.png" alt="服务间通信方式" style="zoom:150%;" />

- 基于Http协议、使用Rest方式通信：使用Http协议进行数据传输——JSON（<font color=red>SpringCloud使用Http协议传输数据！</font>只要都支持Http协议，而不要求统一由Java、Python、C++等开发实现——<font color=red>解耦合</font>），但效率较RPC低。
- [RPC](# 3.微服务的解决方案)：远程过程调用——传输二进制对象序列化形式数据（传输效率高，但对硬件要求高——<font color=red>服务必须都基于同一种语言开发</font>：Java对Java）

总结：`在SpringCloud中服务间调用方式主要是使用http、rest方式进行服务间调用`！



## 7.1.基于RestTemplate的服务调用

<font color=red>地址写死；没有**负载均衡**</font>

```markdown
# 0.说明
- Spring框架提供的RestTemplate类（发起一个Http请求）可用于在应用中调用rest服务，它简化了与http服务的通信方式，统一了RESTful的标准，封装了http链接， 我们只需要传入url及返回值类型即可。相较于之前常用的HttpClient，RestTemplate是一种更优雅的调用RESTful服务的方式。
```



```markdown
# 1.创建两个服务并注册到consul注册中心中
- ConsulClientUsers   代表用户服务，端口为 8888
- ConsulClientOrders 代表订单服务，端口为 9999
注意：这里服务仅仅用来测试，没有实际业务意义
```



```markdown
# 2.在订单服务中提供服务方法
```

````java
package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzufeng
 * @date 2021/06/10
 */

@RestController
@Slf4j
public class OrderController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("FindOrders")
    public Map<String, Object> findOrders() {
        log.info("查询所有订单，调用成功！当前服务端口：[{}]", port);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "服务调用成功，服务提供端口为：" + port);
        map.put("state", true);
        return map;
    }
}
````



```markdown
# 3.在用户服务中使用restTemplate进行调用
```

```java
package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenzufeng
 * @date 2021/06/10
 * 调用订单服务
 */

@RestController
@Slf4j
public class UsersController {
    @GetMapping("UsersServerInvokeOrdersServer")
    public String invokeOrdersServer() {
        log.info("用户信息服务调用订单服务。。。");
        // 使用restTemplate发起请求，调用商品服务
        RestTemplate restTemplate = new RestTemplate();
        String orderResult = restTemplate.getForObject("http://localhost:9999/FindOrders", String.class);
        return "调用订单服务成功，结果为：" + orderResult;
    }
}
```



```markdown
# 4.启动服务
```

先启动“服务注册中心”，再启动两个服务！



```markdown
# 5.测试服务调用
```

浏览器访问用户服务http://localhost:8888/UsersServerInvokeOrdersServer

<img src="SpringCloud微服务工具集.assets/服务间通信方式1.png" alt="image-20210610152511321" style="zoom:50%;" />

```markdown
com.example.controller.UsersController   : 用户信息服务调用订单服务。。。
```



```markdown
# 6.总结
- restTemplate是直接`基于服务地址`调用，`没有在服务注册中心获取服务`，也没有办法完成服务的负载均衡。如果需要实现服务的负载均衡，需要`自己书写服务负载均衡策略`。
```

- `RestTemplate`在进行服务间通信时，调用服务的路径主机和服务端口直接被写死在`url`中，无法实现在服务集群情况下的请求负载均衡！

  - 自定义负载均衡策略（例如随机选择节点）

    ```java
    public String randomHost() {
        List<String> hosts = new ArrayList<>();
        hosts.add("localhost:8888");
        hosts.add("localhost:8889");
        int i = new Random.nextInt(hosts.size());
        return hosts.get(i);
    }
    ```

  - 使用SpringCloud提供的组件：Ribbon（推荐）

- 调用服务的请求路径写死在代码中，日后提供服务的服务路径发生改变时，不利于后续维护工作！

```java
RestTemplate restTemplate = new RestTemplate();
// RestTemplate将地址与端口写死了
String orderResult = restTemplate.getForObject("http://localhost:9999/FindOrders", String.class);
```



## 7.2.基于Ribbon的服务调用

<font color=red>RestTemplate + Ribbon：路径写死在代码中，不利于维护！</font>

```markdown
# 0.说明
- 官方网址: https://github.com/Netflix/ribbon
- Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，可以让我们轻松地将面向服务的REST模版请求，自动转换成客户端负载均衡的服务调用。
- Ribbon是完成负载均衡的客户端组件（客户端负载均衡），仅仅完成了请求的负载均衡节点选择，还是需用通过RestTemplate来发起请求
```

运行原理：

用户服务和订单服务在服务注册中心进行注册，接着集成了Ribbon的用户服务根据服务名，从服务注册中心获取对应服务的列表，然后根据特定的策略选择相应的服务（众多订单服务中的一个），通过RestTemplate向其发送请求！

<img src="SpringCloud微服务工具集.assets/基于Ribbon的服务调用.png" alt="image-20210610164002480" style="zoom:47%;" />

### 7.2.1.Ribbon服务调用

```markdown
# 1.项目中引入依赖
- 说明: 
	1.如果使用的是Eureka Client和Consul Client，无须引入依赖。因为在Eureka、Consul中默认集成了ribbon组件，如果引入，会造成冲突！！！！！！
	2.如果使用的Client中没有ribbon依赖，需要显式引入如下依赖
```



```xml
<!--引入ribbon依赖-->
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    <version>2.2.8.RELEASE</version>
</dependency>
```



```markdown
# 2.查看Consul Client中依赖的ribbon
```

<img src="SpringCloud微服务工具集.assets/基于Ribbon的服务调用1.png" alt="基于Ribbon的服务调用1" style="zoom:50%;" />



```markdown
# 3.使用restTemplate + ribbon进行服务调用
- 使用DiscoveryClient进行客户端调用
	服务发现客户端对象：根据服务id去服务中心获取对应服务的服务列表到本地，但需要自己去实现负载均衡！
- 使用LoadBalancerClient进行客户端调用
	负载均衡客户端对象：根据服务id去服务中心获取对应服务的服务列表，然后根据默认负载均衡策略选择列表中一个对象
	使用时，每次需要先选择，再使用Restemplate
- 使用@LoadBalanced进行客户端调用
	负载均衡客户端注解：直接根据服务id，底层实现负载均衡
	修饰范围：方法；
	作用：让当前对象具有ribbon负载均衡特性
```



```markdown
# 3.1 使用DiscoveryClient形式调用
```

```java
package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author chenzufeng
 * @date 2021/06/10
 * 调用订单服务
 */

@RestController
@Slf4j
public class UsersController {
    /**
     * 服务注册与发现客户端对象
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("UsersServerInvokeOrdersServer")
    public String invokeOrdersServer() {
        log.info("=========================================");
        log.info("用户信息服务调用订单服务。。。");
        /*
        // 1.使用restTemplate发起请求，调用商品服务
        RestTemplate restTemplate = new RestTemplate();
        // <T> T getForObject(URI url, Class<T> responseType)
        String orderResult = restTemplate.getForObject("http://localhost:9999/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
         */

        // 2.使用restTemplate + ribbon进行服务调用

        // 使用DiscoveryClient将服务注册中心的信息拉取到本地，但没有实现负载均衡
        List<ServiceInstance> consulClientOrders = discoveryClient.getInstances("ConsulClientOrders");
        for (ServiceInstance consulClientOrder : consulClientOrders) {
            log.info("服务主机：[{}]", consulClientOrder.getHost());
            log.info("服务端口：[{}]", consulClientOrder.getPort());
            log.info("服务地址：[{}]", consulClientOrder.getUri());
            log.info("=========================================");
        }
	// DiscoveryClient将服务注册中心的信息全部拉取到本地：使用-Dserver.port创建了两个订单服务
        String orderResult = new RestTemplate().getForObject(consulClientOrders.get(0).getUri() + "/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
    }
}
```

使用`-Dserver.port=`创建两个`Orders`应用：

![使用DiscoveryClient形式调用](SpringCloud微服务工具集.assets/使用DiscoveryClient形式调用.png)



<img src="SpringCloud微服务工具集.assets/使用DiscoveryClient形式调用1.png" alt="image-20210610173541610" style="zoom:47%;" />





```markdown
# 3.2 使用LoadBalancerClient形式调用
```

```java
package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author chenzufeng
 * @date 2021/06/10
 * 调用订单服务
 */

@RestController
@Slf4j
public class UsersController {
    /**
     * 2.1.服务注册与发现客户端对象
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 2.2.具有负载均衡策略的客户端对象
     * import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("UsersServerInvokeOrdersServer")
    public String invokeOrdersServer() {
        log.info("=========================================");
        log.info("用户信息服务调用订单服务。。。");
        /*
        // 1.使用restTemplate发起请求，调用商品服务
        RestTemplate restTemplate = new RestTemplate();
        String orderResult = restTemplate.getForObject("http://localhost:9999/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
         */

        // 2.使用restTemplate + ribbon进行服务调用

        /*
        // 2.1.使用DiscoveryClient将服务注册中心的信息拉取到本地，但没有实现负载均衡
        List<ServiceInstance> consulClientOrders = discoveryClient.getInstances("ConsulClientOrders");
        for (ServiceInstance consulClientOrder : consulClientOrders) {
            log.info("服务主机：[{}]", consulClientOrder.getHost());
            log.info("服务端口：[{}]", consulClientOrder.getPort());
            log.info("服务地址：[{}]", consulClientOrder.getUri());
            log.info("=========================================");
        }

        String orderResult = new RestTemplate().getForObject(consulClientOrders.get(0).getUri() + "/FindOrders", String.class);

        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
         */

        // 2.2.使用LoadBalancerClient进行服务调用——默认策略是轮询
        ServiceInstance serviceInstance = loadBalancerClient.choose("ConsulClientOrders");
        log.info("服务主机：[{}]；服务端口：[{}]；服务地址：[{}]", serviceInstance.getHost(), serviceInstance.getPort(), serviceInstance.getUri());
        String orderResult = new RestTemplate().getForObject(serviceInstance.getUri() + "/FindOrders", String.class);

        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
    }
}
```

![使用LoadBalancerClient形式调用](SpringCloud微服务工具集.assets/使用LoadBalancerClient形式调用.png)



```markdown
# 3.3 使用@LoadBalanced
```

在`config`包中创建`BeansConfig`：

```java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenzufeng
 * 1.整合restTemplate + ribbon
 */

@Configuration
public class BeansConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```



```java
//2.调用服务位置注入RestTemplate
@Autowired
private RestTemplate restTemplate;
//3.调用
String forObject = restTemplate.getForObject("http://服务ID/hello/hello?name=" + name, String.class);
```

```java
package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author chenzufeng
 * @date 2021/06/10
 * 调用订单服务
 */

@RestController
@Slf4j
public class UsersController {
    /**
     * 2.1.服务注册与发现客户端对象
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 2.2.具有负载均衡策略的客户端对象
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("UsersServerInvokeOrdersServer")
    public String invokeOrdersServer() {
        log.info("=========================================");
        log.info("用户信息服务调用订单服务。。。");
        /*
        // 1.使用restTemplate发起请求，调用商品服务
        RestTemplate restTemplate = new RestTemplate();
        String orderResult = restTemplate.getForObject("http://localhost:9999/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
         */

        // 2.使用restTemplate + ribbon进行服务调用

        /*
        // 2.1.使用DiscoveryClient将服务注册中心的信息拉取到本地，但没有实现负载均衡
        List<ServiceInstance> consulClientOrders = discoveryClient.getInstances("ConsulClientOrders");
        for (ServiceInstance consulClientOrder : consulClientOrders) {
            log.info("服务主机：[{}]", consulClientOrder.getHost());
            log.info("服务端口：[{}]", consulClientOrder.getPort());
            log.info("服务地址：[{}]", consulClientOrder.getUri());
            log.info("=========================================");
        }

        String orderResult = new RestTemplate().getForObject(consulClientOrders.get(0).getUri() + "/FindOrders", String.class);

        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
         */

        /*
        // 2.2.使用LoadBalancerClient进行服务调用——默认策略是轮询
        ServiceInstance serviceInstance = loadBalancerClient.choose("ConsulClientOrders");
        log.info("服务主机：[{}]；服务端口：[{}]；服务地址：[{}]", serviceInstance.getHost(), serviceInstance.getPort(), serviceInstance.getUri());
        String orderResult = new RestTemplate().getForObject(serviceInstance.getUri() + "/FindOrders", String.class);

        log.info("调用订单服务成功，结果为{}", orderResult);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + orderResult;
	 */
        
        // 2.3.使用@LoadBalanced让当前对象具有ribbon负载均衡特性
        String result = restTemplate.getForObject("http://ConsulClientOrders/FindOrders", String.class);
        log.info("调用订单服务成功，结果为{}", result);
        log.info("=========================================");
        return "调用订单服务成功，结果为：" + result;
    }
}
```

![使用@LoadBalanced](SpringCloud微服务工具集.assets/使用@LoadBalanced.png)



### 7.2.2.Ribbon负载均衡策略

#### 版本问题

<font color=red>有问题！</font>

问题追根：在最新的Eureka和Consul中没有显示存在默认`spring-cloud-starter-netflix-ribbon`依赖

可能与使用的版本有关！

使用：

```xml
<properties>
    <spring.cloud-version>Hoxton.SR8</spring.cloud-version>
</properties>

<!--继承SpringBoot的父项目-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.5.RELEASE</version>
</parent>
```

Maven结构：

<img src="SpringCloud微服务工具集.assets/Ribbon负载均衡策略.png" alt="image-20210611140511254" style="zoom:50%;" />

使用：

```xml
<!--继承SpringBoot的父项目-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <version>2.5.0</version>
    <artifactId>spring-boot-starter-parent</artifactId>
</parent>

<!--自定义properties属性-->
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
    <!--定义SpringCloud使用版本号-->
    <spring.cloud-version>2020.0.3</spring.cloud-version>
</properties>
```

Maven结构：

<img src="SpringCloud微服务工具集.assets/Ribbon负载均衡策略1.png" alt="image-20210611140748102" style="zoom:50%;" />



#### 负载均衡策略

```markdown
- RoundRobinRule         	      轮训策略：按顺序循环选择Server
- RandomRule             		随机策略：随机选择Server
- AvailabilityFilteringRule        可用过滤策略：会先过滤由于多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问

- WeightedResponseTimeRule  响应时间加权策略：根据平均响应的时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越高，刚启动时如果统计信息不足，则使用RoundRobinRule策略，等统计信息足够会切换到

- RetryRule                              重试策略：先按照RoundRobinRule的策略获取服务，如果获取失败则在制定时间内进行重试，获取可用的服务。
	
- BestAviableRule                    最低并发策略：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务  
```

![Ribbon负载均衡策略2](SpringCloud微服务工具集.assets/Ribbon负载均衡策略2.png)



### 7.2.3.修改服务的默认负载均衡策略

```markdown
# 1.修改服务默认随机策略
- 服务id.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
在ConsulClientUsers\src\main\resources\application.properties中添加
- ConsulClientOrders.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
```



### 7.2.4.Ribbon问题与停止维护

<font color=red>RestTemplate + Ribbon：路径写死在代码中，不利于维护！</font>

```java
String result = restTemplate.getForObject("http://ConsulClientOrders/FindOrders", String.class);
```



```markdown
# 1.官方停止维护说明
- https://github.com/Netflix/ribbon
```

![Ribbon停止维护](SpringCloud微服务工具集.assets/Ribbon停止维护.png)



# 8.OpenFeign组件的使用

<font color=red>伪Http客户端组件；调用存在负载均衡；支持SpringMVC注解（解耦）</font>

- 思考：使用RestTemplate+ribbon已经可以完成对端的调用，为什么还要使用feign？

```java
String restTemplateForObject = restTemplate.getForObject("http://服务名/url?参数" + name, String.class);
```

```markdown
# 存在问题:
- 1. 每次调用服务都需要写这些代码，存在大量的代码冗余
- 2. 服务地址如果修改，维护成本增高
- 3. 使用时不够灵活
```

OpenFeign组件

```markdown
# 0.说明
- https://cloud.spring.io/spring-cloud-openfeign/reference/html/
- 实现服务间通信的组件
- Feign是一个声明式的`伪Http客户端`（代理服务器——伪服务器），它使得写Http客户端变得更简单。
- 使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性(可以使用SpringMVC的注解)，可使用Feign注解和JAX-RS（Rest——Get、Post……）注解。Feign支持可插拔的编码器和解码器。
- Feign默认集成了Ribbon，默认实现了负载均衡的效果，并且SpringCloud为Feign添加了SpringMVC注解的支持。
```

## 8.1.openFeign服务调用

<img src="SpringCloud微服务工具集.assets/OpenFeign组件.png" alt="OpenFeign组件" style="zoom:50%;" />

- 创建两个服务，并注册到服务注册中心（SpringBoot应用+引入Consul依赖、健康检查依赖+注册+入口类加注解）
- 使用openFeign进行服务调用：类别服务调用商品服务

```markdown
# 1.服务调用方（类别服务）引入OpenFeign依赖
```

```xml
<!--Open Feign依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```



```markdown
# 2.服务调用方（类别服务）入口类加入注解开启OpenFeign支持
```

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 开启服务注册
@EnableDiscoveryClient
// 开启OpenFeogn客户端调用
@EnableFeignClients
public class OpenFeignCategoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignCategoryApplication.class, args);
    }
}
```



```markdown
# 3.在Product创建一个客户端调用接口
```

创建`feignClient`包，并调用哪个服务，就创建哪个服务的接口：

```java
package com.example.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    /**
     * 与ProductController.java中方法名一致
     * @return String
     */
    @GetMapping("Product")
    String getProduct();
}
```



```markdown
# 4.使用FeignClient客户端对象调用服务
```

```java
package com.example.controller;

import com.example.feignClient.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");
        String product = productClient.getProduct();
        log.info("品类服务调用商品服务{}", product);
        log.info("======================");
        return "品类服务调用商品服务：" + product;
    }
}
```



```markdown
# 5.访问并测试服务
```

<img src="SpringCloud微服务工具集.assets/OpenFeign组件1.png" alt="OpenFeign组件1" style="zoom:50%;" />



```markdown
# 6.验证负载均衡
```

![OpenFeign组件2](SpringCloud微服务工具集.assets/OpenFeign组件2.png)



## 8.2.参数传递与响应处理

服务间通信手段：Http协议；

SpringCloud采用两种方法：

- RestTemplate + Ribbon
- OpenFeign（推荐）

服务和服务之间通信，不仅仅是调用，往往在调用过程中还伴随着参数传递

- 传递零散类型参数：
  - `Query String`方式传递参数：`?value=1`
  - 路径传递参数：`/value/1`
- 传递对象类型
- 数组或集合类型参数



### 8.2.1.传递简单参数

查询、删除时使用GET方式调用服务传递参数

```markdown
- 在商品服务中加入需要传递参数的服务方法来进行测试
- 在品类服务中调用商品服务中需要传递参数的服务方法进行测试
```

在商品服务中定义接收零星参数的接口：

```java
package com.example.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    /**
     * 与ProductController.java中方法名一致
     * @return String
     */
    @GetMapping("Product")
    String getProduct();

    /**
     * 声明调用服务中的接口，传递name, age
     * @param name
     * @param age
     * @return
     */
    @GetMapping("ParameterQueryString")
    String getParameterParameterQueryString(String name, Integer age);
}
```

在商品服务中实现对应方法`public String getParameterQueryString(String name, Integer age)`：

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("Product")
    public String getProduct() {
        log.info("======================");
        log.info("进入商品服务......");
        log.info("提供当前服务的端口[{}]", port);
        log.info("======================");
        return "提供商品服务的端口为" + port;
    }

    /**
     * 接收零星参数（queryString）
     * @param name 姓名
     * @param age 年龄
     * @return String
     */
    @GetMapping("ParameterQueryString")
    public String getParameterQueryString(String name, Integer age) {
        log.info("name={}  age={}", name, age);
        return "提供商品服务的端口为" + port;
    }
}
```

在`CategoryController.java`中调用服务并传参——`productClient.getParameterQueryString`：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");

        String product = productClient.getProduct();
        log.info("品类服务调用商品服务中getProduct方法：{}", product);

        String parameterQueryString = productClient.getParameterQueryString("chenzf", 27);
        log.info("品类服务调用商品服务中getParameterQueryString方法：{}", parameterQueryString);

        log.info("======================");
        return "品类服务调用商品服务中getProduct方法：" + product + " 和getParameterQueryString方法：" + parameterQueryString;
    }
}
```



```markdown
# 出现问题
	Caused by: java.lang.IllegalStateException: Method has too many Body parameters: public abstract java.lang.String com.example.feignClient.ProductClient.getParameter(java.lang.String,java.lang.Integer)
```

传参数时，没有明确告知OpenFeign的传参方式（QueryString还是路径传参），因此不知道该如何组合多参数！



#### 明确传参方式

- Query String方式传参（`?name=chen`）：在OpenFeign接口声明中必须给参数加入注解`@RequestParam`

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    /**
     * 与ProductController.java中方法名一致
     * @return String
     */
    @GetMapping("Product")
    String getProduct();

    /**
     * 声明调用服务中的接口，传递name, age
     * /ParameterQueryString?aa=xx&age=xx
     * @param name
     * @param age
     * @return
     */
    @GetMapping("ParameterQueryString")
    // /Parameter?aa=xx&age=xx
    String getParameterQueryString(@RequestParam("aa") String name, @RequestParam("age") Integer age);
}
```

- 路径传参（`/name/chen`）：在OpenFeign接口声明中必须给参数加入注解`@PathVariable`

`ProductClient.java`

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    /**
     * 与ProductController.java中方法名一致
     * @return String
     */
    @GetMapping("Product")
    String getProduct();

    /**
     * 声明调用服务中的接口，传递name, age
     * /ParameterQueryString?name=xx&age=xx（QueryString方式）
     * @param name
     * @param age
     * @return
     */
    @GetMapping("ParameterQueryString")
    String getParameterQueryString(@RequestParam("name") String name, @RequestParam("age") Integer age);

    /**
     * 声明调用服务中的接口，传递name, age
     * /Parameter/{name}/{age}（路径传参）
     * @param name
     * @param age
     * @return
     */
    @GetMapping("Parameter/{name}/{age}")
    String getParameter(@PathVariable("name") String name, @PathVariable("age") Integer age);
}
```

`ProductController.java`

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("Product")
    public String getProduct() {
        log.info("======================");
        log.info("进入商品服务......");
        log.info("提供当前服务的端口[{}]", port);
        log.info("======================");
        return "提供getProduct商品服务的端口为" + port;
    }

    /**
     * 接收零星参数（queryString）
     * @param name 姓名
     * @param age 年龄
     * @return String
     */
    @GetMapping("ParameterQueryString")
    public String getParameterQueryString(String name, Integer age) {
        log.info("name={}  age={}", name, age);
        return "提供getParameterQueryString商品服务的端口为" + port;
    }

    @GetMapping("Parameter/{name}/{age}")
    // 必须使用@PathVariable
    public String getParameter(@PathVariable("name") String name, @PathVariable("age") Integer age) {
        log.info("name={}  age={}", name, age);
        return "提供getParameter商品服务的端口为" + port;
    }
}
```

在`CategoryController.java`中调用服务并传参——`productClient.getParameter`：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");

        String product = productClient.getProduct();
        log.info("品类服务调用商品服务中getProduct方法：{}", product);

        String parameterQueryString = productClient.getParameterQueryString("chenzf", 27);
        log.info("品类服务调用商品服务中getParameterQueryString方法：{}", parameterQueryString);

        String parameter = productClient.getParameter("zufeng", 28);
        log.info("品类服务调用商品服务中getParameter方法：{}", parameter);

        log.info("======================");
        return "品类服务调用商品服务中getProduct方法：" + product +
                " 和getParameterQueryString方法：" + parameterQueryString +
                " 和getParameter方法：" + parameter;
    }
}
```



### 8.2.2.传递对象类型参数

添加、修改时使用post方式调用服务传递参数

在商品和品类项目中创建对象：`entity(Product.java)`

```java
package com.example.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author chenzufeng
 */

@Data
public class Product {
    private Integer id;
    private String name;
    private Double salary;
    private Date birth;
}
```

1. 商品服务加入post方式请求并接受参数——`@RequestBody`

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;
    
    //......

    /**
     * 定义一个接受对象类型参数的接口
     * RequestBody以Json格式传递数据
     */
    @PostMapping("ObjectParameter")
    public String getObjectParameter(@RequestBody Product product) {
        log.info("Product对象：{}", product);
        return "提供getObjectParameter商品服务的端口为" + port;
    }
}
```



2. 品类服务中在ProductClient客户端中声明方法——`@RequestBody`

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {

    // ........
    /**
     * 声明调用服务中的接口，传递name, age
     * /Parameter/{name}/{age}（路径传参）
     * 必须有@PathVariable
     */
    @GetMapping("Parameter/{name}/{age}")
    String getParameter(@PathVariable("name") String name, @PathVariable("age") Integer age);

    /**
     * 定义一个接受对象类型参数的接口，传递Product对象
     * @return String
     * RequestBody以Json格式传递
     */
    @PostMapping("ObjectParameter")
    String getObjectParameter(@RequestBody Product product);
}
```

3. 品类服务中调用并传递参数

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");

       //......

        String objectParameter = productClient.getObjectParameter(new Product(1, "chen", 20000.0, new Date()));
        log.info("品类服务调用商品服务中objectParameter方法：{}", objectParameter);

        log.info("======================");
        return "品类服务调用商品服务中getProduct方法：" + product + "\n" + 
                " 和getParameterQueryString方法：" + parameterQueryString + "\n" + 
                " 和getParameter方法：" + parameter + "\n" + 
                " 和getObjectParameter方法：" + objectParameter;
    }
}
```



4. 测试

![OpenFeign传递参数](SpringCloud微服务工具集.assets/OpenFeign传递参数.png)



### 8.2.3.传递数组和集合类型参数

#### 数组类型的参数传递

传递数组类型参数，只能使用`Query String`形式！

在`ProductController`中定义接口接收数组类型参数

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    // ......

    /**
     * 定义接口接收数组类型参数
     * @param ids id数组
     * @return String
     */
    @GetMapping("ArrayParameter")
    public String getArrayParameter(String[] ids) {
        for (String id : ids) {
            log.info("id: {}", id);
        }
        return "提供getArrayParameter商品服务的端口为" + port;
    }
}
```



在品类`feignClient`的`ProductClient`中声明调用商品服务中`getArrayParameter`接口，传递数组类型参数——`@RequestParam("ids")`

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    // ......
    /**
     * 声明声明调用商品服务中`getArrayParameter`接口，传递数组类型参数
     * @param ids id数组
     * @return String
     */
    @GetMapping("ArrayParameter")
    String getArrayParameter(@RequestParam("ids") String[] ids);
}
```



在`CategoryController`中调用商品服务：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");

	// ......

        String arrayParameter = productClient.getArrayParameter(new String[]{"chen", "zu", "feng"});
        log.info("品类服务调用商品服务中getArrayParameter方法：{}", arrayParameter);

        log.info("======================");
        return "品类服务调用商品服务中getProduct方法：" + product + "\n" +
                " 和getParameterQueryString方法：" + parameterQueryString + "\n" +
                " 和getParameter方法：" + parameter + "\n" +
                " 和getObjectParameter方法：" + objectParameter + "\n" +
                " 和getArrayParameter方法：" + arrayParameter;
    }
}
```



输出结果：

```markdown
c.example.controller.ProductController   : id: chen
c.example.controller.ProductController   : id: zu
c.example.controller.ProductController   : id: feng
```



#### 集合类型的参数传递

在`OpenFeignProduct/vos`包中创建`CollectionValueObject`（VO：Value Object，用来传递数据对象）：

```java
package com.example.vos;

import java.util.List;

/**
 * @author chenzufeng
 * 定义用来接收集合类型参数的对象
 */
public class CollectionValueObject {
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
```

在`ProductController`中定义一个接口接收集合类型参数：

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    // .....

    /**
     * 定义一个接口接收集合类型参数
     */
    @GetMapping("ListParameter")
    public String getListParameter(CollectionValueObject collectionValueObject) {
        collectionValueObject.getIds().forEach(id -> log.info("id：{}", id));
        return "提供getListParameter商品服务的端口为" + port;
    }
}
```



在`ProductClient`中声明调用商品服务中`getListParameter`接口，传递一个List集合类型参数：

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    // ......

    /**
     * 声明声明调用商品服务中`getArrayParameter`接口，传递数组类型参数
     * getListParameter?ids=1&ids=2
     * @param ids id数组
     * @return String
     */
    @GetMapping("ArrayParameter")
    String getArrayParameter(@RequestParam("ids") String[] ids);

    /**
     * 声明调用商品服务中getListParameter接口，传递一个List集合类型参数
     * getListParameter?ids=1&ids=2
     * @param ids ids
     * @return String
     */
    @GetMapping("ListParameter")
    String getListParameter(@RequestParam("ids") String[] ids);
}
```

在`CategoryController`中调用服务：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");

        // ......

        String listParameter = productClient.getListParameter(new String[]{"c", "z", "f"});
        log.info("品类服务调用商品服务中getListParameter方法：{}", listParameter);

        log.info("======================");
        return "品类服务调用商品服务中getProduct方法：" + product + "\n" +
                " 和getParameterQueryString方法：" + parameterQueryString + "\n" +
                " 和getParameter方法：" + parameter + "\n" +
                " 和getObjectParameter方法：" + objectParameter + "\n" +
                " 和getArrayParameter方法：" + arrayParameter + "\n" + 
                " 和getListParameter方法：" + listParameter;
    }
}
```



### 8.2.4.响应处理

#### 使用OpenFeign调用服务并返回对象

- 类别服务根据`id`调用商品服务，商品服务再返回查询到的商品对象！

在`ProductController`中定义一个接口，接收id类型参数，并返回一个基于id查询到的对象：

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    // ......

    /**
     * 定义一个接口，接收id类型参数，并返回一个基于id查询到的对象
     * 使用路径传递方式
     * @param id id
     * @return String
     */
    @GetMapping("ReturnProduct/{id}")
    public Product returnProduct(@PathVariable("id") Integer id) {
        log.info("id：{}", id);
        return new Product(1, "祖峰", 16000.0, new Date());
    }
}
```

在`OpenFeignCategory/feignClient/ProductClient`中声明调用根据id查询商品信息接口：

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    // ......

    /**
     * 声明调用根据id查询商品信息接口
     * @param id id
     * @return String
     */
    @GetMapping("ReturnProduct/{id}")
    Product returnProduct(@PathVariable("id") Integer id);
}
```

在`CategoryController`中调用服务：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Product")
    public Product getProduct() {
        log.info("======================");
        log.info("进入品类服务......");
        Product returnedProduct = productClient.returnProduct(1);
        log.info("品类服务调用商品服务中returnProduct方法：{}", returnedProduct);
        log.info("======================");
        return returnedProduct;
    }
}
```

<img src="SpringCloud微服务工具集.assets/OpenFeign响应处理.png" alt="OpenFeign响应处理" style="zoom:50%;" />

#### 使用OpenFeign调用服务并返回List对象

- 类别服务根据类别`id`调用商品服务，商品服务再返回查询到的商品对象List集合！

在`ProductController`中定义一个接口，接收categoryId类型参数，并返回一个List集合：

```java
package com.example.controller;

import com.example.entity.Product;
import com.example.vos.CollectionValueObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    // .......

    @GetMapping("CategoryProduct")
    public List<Product> findByCategoryId(Integer categoryId) {
        // 调用业务逻辑，根据id查询商品列表并返回
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "chen", 16000.0, new Date()));
        products.add(new Product(2, "zufeng", 20000.0, new Date()));
        return products;
    }
}
```

在`OpenFeignCategory/feignClient/ProductClient`中声明调用根据id查询商品信息接口：

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
   // .......

    /**
     * 声明调用商品服务根据categoryId查询一组商品信息并返回
     * @param categoryId
     * @return
     */
    @GetMapping("CategoryProduct")
    List<Product> findByCategoryId(@RequestParam("categoryId") Integer categoryId);
}
```

在`CategoryController`中调用服务：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    // ......
    
    @GetMapping("ListProduct")
    public List<Product> getListProduct() {
        log.info("======================");
        log.info("进入品类服务......");
        List<Product> products = productClient.findByCategoryId(1);
        products.forEach(product -> log.info("Product：{}", product));
        log.info("品类服务调用商品服务中returnProduct方法：{}", products);
        log.info("======================");
        return products;
    }
}
```

测试地址：http://localhost:8787/ListProduct

```markdown
[{"id":1,"name":"chen","salary":16000.0,"birth":"2021-06-15T09:53:47.421+0000"},{"id":2,"name":"zufeng","salary":20000.0,"birth":"2021-06-15T09:53:47.421+0000"}]
```

#### 使用OpenFeign调用服务并返回Map复杂对象

在`ProductController`中定义一个接口，接收categoryId类型参数，并返回一个Map集合，实现分页展示：

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    // ......

    /**
     * 定义一个接口，接收categoryId类型参数，并返回一个基于categoryId查询到的List对象
     * @param categoryId categoryId
     * @return String
     */
    @GetMapping("CategoryProductMap")
    public Map<String, Object> findByCategoryIdAndPage(Integer page, Integer rows, Integer categoryId) {
        log.info("当前页：{}；每页显示记录数：{}；当前类别id：{} ", page, rows, categoryId);
        /*
         * 根据类别id分页查询符合当前页集合数据  List<Product>
         *      select * from t_product where categoryId=? limt ?(page-1)*rows, ?(rows)
         * 根据类别id查询当前类别下总条数       totalCount
         *      select count(id) from t_product where categoryId=?
         */
        Map<String, Object> map = new HashMap<>();
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "chen", 16000.0, new Date()));
        products.add(new Product(2, "zufeng", 20000.0, new Date()));
        long totalCount = 1000;
        map.put("rows", products);
        map.put("total", totalCount);
        return map;
    }
}
```

在`OpenFeignCategory/feignClient/ProductClient`中声明调用根据id查询商品信息接口：

```java
/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    // .......

    /**
     * 声明调用商品服务根据类别id查询分页查询商品信息以及总条数
     * @param page page
     * @param rows rows
     * @param categoryId categoryId
     * @return Map<String, Object>
     */
    @GetMapping("CategoryProductMap")
    Map<String, Object> findByCategoryIdAndPage(
            @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("categoryId") Integer categoryId);
}
```

在`CategoryController`中调用服务：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;
    
    @GetMapping("MapProduct")
    public Map<String, Object> getMapProduct() {
        Map<String, Object> objectMap = productClient.findByCategoryIdAndPage(1, 5, 1);
        return objectMap;
    }
}
```

测试结果：

```markdown
{"total":1000,"rows":[{"id":1,"name":"chen","salary":16000.0,"birth":"2021-06-15T10:52:20.679+0000"},{"id":2,"name":"zufeng","salary":20000.0,"birth":"2021-06-15T10:52:20.679+0000"}]}
```

注意，这里的`1000`是`Object`——`Map<String, Object>`

由于`Map`被转换为`Json`进行传递，因此，将`ProductClient`由原来的：

```java
Map<String, Object> findByCategoryIdAndPage(
            @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("categoryId") Integer categoryId);
```

修改为：

```java
String findByCategoryIdAndPage(
            @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("categoryId") Integer categoryId);
```

相应的，将`CategoryController`修改为：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    // ......
    @GetMapping("MapProduct")
    public String getMapProduct() {
        String objectMapString = productClient.findByCategoryIdAndPage(1, 5, 1);
        return objectMapString;
    }
}
```

在`OpenFeignCategory`中引入`fastjson`：

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.76</version>
</dependency>
```

将`CategoryController`修改为：

```java
@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    // ........
    
    @GetMapping("MapProduct")
    public String getMapProduct() {
        String objectMapString = productClient.findByCategoryIdAndPage(1, 5, 1);

        /*
         * 自定义Json反序列化（对象转为Json——序列化；Json字符串转为对象——反序列化）
         * import com.alibaba.fastjson.JSONObject;
         * public class JSONObject extends JSON implements Map<String, Object>...
         */
        JSONObject jsonObject = JSONObject.parseObject(objectMapString);
        log.info("======================");
        log.info("total：{}", jsonObject.get("total"));
        log.info("rows：{}", jsonObject.get("rows"));
        log.info("======================");

        /*
         * 二次Json反序列化
         * rows：[{"name":"chen","birth":"2021-06-15T11:17:10.276+0000","id":1,"salary":16000.0},
         *            {"name":"zufeng","birth":"2021-06-15T11:17:10.276+0000","id":2,"salary":20000.0}]
         */
        Object rows = jsonObject.get("rows");
        List<Product> products = JSONObject.parseArray(rows.toString(), Product.class);
        products.forEach(product -> log.info("product：{}", product));

        return objectMapString;
    }
}
```

测试结果：

```markdown
c.example.controller.CategoryController  : ======================
c.example.controller.CategoryController  : total：1000
c.example.controller.CategoryController  : rows：[{"name":"chen","birth":"2021-06-15T11:26:40.785+0000","id":1,"salary":16000.0},{"name":"zufeng","birth":"2021-06-15T11:26:40.785+0000","id":2,"salary":20000.0}]
c.example.controller.CategoryController  : ======================
c.example.controller.CategoryController  : product：Product(id=1, name=chen, salary=16000.0, birth=Tue Jun 15 19:26:40 CST 2021)
c.example.controller.CategoryController  : product：Product(id=2, name=zufeng, salary=20000.0, birth=Tue Jun 15 19:26:40 CST 2021)
```





## 8.3.Feign超时设置

```markdown
# 0.超时说明
- 默认情况下，openFiegn在进行服务调用时，要求服务提供方处理业务逻辑时间必须在1S内返回。如果超过1S没有返回则OpenFeign会直接报错，不会等待服务执行。
- 但是往往在处理复杂业务逻辑是可能会超过1S，因此需要修改OpenFeign的默认服务调用超时时间。
```

```markdown
# 1.模拟超时
- `服务提供方`加入线程等待阻塞
```

![Feign超时设置](SpringCloud微服务工具集.assets/Feign超时设置.png)

```markdown
# 2.进行客户端调用
```

![Feign超时设置1](SpringCloud微服务工具集.assets/Feign超时设置1.png)

```markdown
# 3.修改OpenFeign默认超时时间(在类别服务中)
```

```properties
feign.client.config.PRODUCTS.connectTimeout=5000  		# 配置指定服务连接超时
feign.client.config.PRODUCTS.readTimeout=5000		     # 配置指定服务等待超时
#feign.client.config.default.connectTimeout=5000  		    # 配置所有服务连接超时
#feign.client.config.default.readTimeout=5000			 # 配置所有服务等待超时
```



## 8.4.OpenFeign调用详细日志展示

```markdown
# 0.说明
- 往往在服务调用时，我们需要详细展示feign的日志。默认feign在调用是并不是最详细日志输出，因此在调试程序时应该开启feign的详细日志展示。
- feign对日志的处理非常灵活，可为每个feign客户端指定日志记录策略。每个客户端都会创建一个logger默认情况下logger的名称是feign的全限定名。需要注意的是，feign日志的打印只会DEBUG级别做出响应。
- 我们可以为feign客户端配置各自的logger.lever对象，告诉feign记录那些日志logger.lever有以下的几种值
	`NONE  不记录任何日志
	`BASIC 仅仅记录请求方法，url，响应状态代码及执行时间
	`HEADERS 记录Basic级别的基础上，记录请求和响应的header
	`FULL 记录请求和响应的header，body和元数据
```

```markdown
# 1.开启日志展示（类别服务）
```

```properties
feign.client.config.PRODUCTS.loggerLevel=full  # 开启指定服务日志展示
#feign.client.config.default.loggerLevel=full     # 全局开启服务日志展示
logging.level.com.example.feignClients=debug     # 指定feign调用客户端对象所在包，必须是debug级别
```

```markdown
# 2.测试服务调用查看日志
```

![OpenFeign调用详细日志展示](SpringCloud微服务工具集.assets/OpenFeign调用详细日志展示.png)



# 9.Hystrix组件使用

![Hystrix组件](SpringCloud微服务工具集.assets/Hystrix组件.png)

In a distributed environment, inevitably some of the many service dependencies will fail. Hystrix is a library that helps you control the interactions between these distributed services by adding latency tolerance and fault tolerance logic. Hystrix does this by isolating points of access between the services, stopping cascading failures across them, and providing fallback options, all of which improve your system’s overall resiliency.

```markdown
# 0.说明
- https://github.com/Netflix/Hystrix
- 在分布式环境中，许多服务依赖项不可避免地会失败。
- Hystrix是一个库，它通过添加【延迟容忍】和【容错逻辑】来帮助您控制这些分布式服务之间的交互。
- Hystrix通过隔离服务之间的访问点、停止它们之间的级联故障以及提供后备选项来实现这一点，所有这些都可以提高系统的整体弹性。

- 通俗定义: Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统中，许多依赖不可避免的会调用失败，超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障(服务雪崩现象)，提高分布式系统的弹性。

# 1.作用
- hystrix用来保护微服务系统，实现服务降级、服务熔断
```

## 9.1.服务雪崩

```markdown
# 1.服务雪崩
- 在微服务之间进行服务调用时，由于某一个服务故障，导致级联服务故障的现象，称为雪崩效应。
- 雪崩效应描述的是提供方不可用，导致消费方不可用，并将不可用逐渐放大的过程。
```

```markdown
# 2.图解雪崩效应
```

![服务雪崩](SpringCloud微服务工具集.assets/服务雪崩.png)

```markdown
- 此时，Service A的流量波动很大，流量经常会突然性增加！那么在这种情况下，就算Service A能扛得住请求，Service B和Service C未必能扛得住这突发的请求。
- 此时，如果Service C因为抗不住请求，变得不可用。那么Service B的请求也会阻塞，慢慢耗尽Service B的线程资源，Service B就会变得不可用。紧接着，Service A也会不可用，这一过程如下图所示
```

![服务雪崩1](SpringCloud微服务工具集.assets/服务雪崩1.png)

## 9.2.服务熔断

```markdown
# 服务熔断
- “熔断器”本身是一种【开关装置】，当某个服务单元发生故障之后，通过断路器(hystrix)的故障监控，某个异常条件被触发，【直接熔断整个服务】。【向调用方法返回一个符合预期的、可处理的备选响应】(FallBack)，而不是长时间的等待或者抛出调用方法无法处理的异常
- 保证了【服务调用方的线程不会被长时间占用】，避免故障在分布式系统中蔓延，乃至雪崩。如果【目标服务情况好转】则恢复调用。
- 服务熔断是【解决服务雪崩】的重要手段。
- 【所有微服务】都必须引入Hystrix组件
```

```markdown
# 服务熔断图示
```



![服务熔断](SpringCloud微服务工具集.assets/服务熔断.png)

## 9.3.服务降级

```markdown
# 服务降级说明
- 服务压力剧增的时候，根据当前的业务情况及流量，对一些服务和页面有策略的降级，以此缓解服务器的压力，以【保证核心任务】的进行。同时保证部分甚至大部分任务客户能得到正确的响应。也就是当前的请求处理不了了或者出错了，给一个默认的返回。

- 服务降级：关闭微服务系统中某些【边缘服务】，保证系统【核心服务】正常运行

- 例如双十一，为了确保【下单服务】的正常运行，关闭【评价以往订单】服务！
```

```markdown
# 服务降级图示
```



![服务降级](SpringCloud微服务工具集.assets/服务降级.png)

## 9.4.降级和熔断总结

```markdown
# 1.共同点
- 目的很一致，都是从可用性可靠性着想，为防止系统的整体缓慢甚至崩溃，采用的技术手段；
- 最终表现类似，对于两者来说，最终让用户体验到的是【某些功能暂时不可达或不可用】；
- 粒度一般都是服务级别，当然，业界也有不少更细粒度的做法，比如做到数据持久层（允许查询，不允许增删改）；
- 自治性要求很高，熔断模式一般都是服务基于策略的【自动触发】；降级虽说可【人工干预】，但在微服务架构下，完全靠人显然不可能，开关预置、配置中心都是必要手段

# 2.异同点
- 【触发原因】不太一样
	服务熔断一般是【某个服务（下游服务）故障】引起，而服务降级一般是从【整体负荷】考虑；
- 【管理目标的层次】不太一样
	熔断其实是一个【框架级】的处理，每个微服务都需要（无层级之分）；
	而降级一般需要对业务有层级之分（比如降级一般是从最外围服务边缘服务开始）

# 3.总结
- 【熔断必会触发降级】，所以熔断也是降级一种。
- 区别在于熔断是【对调用链路的保护】，【解决服务雪崩】；而降级是【对系统过载】的一种保护处理
```

## 9.5.服务熔断的实现

```markdown
# 服务熔断的实现思路
- 创建项目
- 在【所有微服务】中引入hystrix依赖，并开启熔断器(断路器)
```

```markdown
# 0.创建项目
	D:\Learning\SpringCloud\SpringCloudCode\SpringCloudDemo\SpringCloudDemoParent\HystrixClient
```

`pom.xml`

```xml
<dependencies>
    <!--SpringBootWeb-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--引入Consul依赖-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>

    <!--引入健康检查依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

`application.properties`

```properties
spring.application.name=HystrixClient

# 注册Consul服务的主机
spring.cloud.consul.host=localhost
# 注册Consul服务的端口号
spring.cloud.consul.port=8500
```

`HystrixApplication`

```java
@SpringBootApplication
@EnableDiscoveryClient
public class HystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}
```



```markdown
# 1.项目中引入hystrix依赖 
```

```xml
<!--引入hystrix-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```



```markdown
# 2.开启断路器：在入口类加入注解EnableCircuitBreaker
```

```java
/**
 * @author chenzufeng
 * SpringBootApplication：代表SpringBoot应用
 * EnableDiscoveryClient：代表服务注册中心Consul客户端
 * EnableCircuitBreaker：开启Hystrix断路器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class HystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}
```



```markdown
# 3.使用HystrixCommand注解实现断路
```

```java
package com.example.controller;

@RestController
@Slf4j
public class HystrixController {

    /**
     * 通过localhost:8080/FirstHystrix?id=xx的方式传递Id
     * HystrixCommand：指定熔断时快速返回方法；
     *                             该方法参数列表、返回值需与HystrixCommand修饰的方法一致
     * @return String
     */
    @GetMapping("FirstHystrix")
    @HystrixCommand(fallbackMethod = "testFallBack")
    public String invokeFirstHystrix(Integer id) {
        log.info("============================");
        log.info("First Hystrix Demo !");
        if (id < 0) {
            throw new RuntimeException("无效Id！");
        }
        log.info("============================");
        return "First Hystrix demo is done !";
    }

    /**
     * 一旦熔断后，不再运行throw new RuntimeException("无效Id！")，而运行testFallBack
     * @param id id
     * @return String
     */
    public String testFallBack(Integer id) {
        return "当前服务繁忙，请稍后再试！不合法Id：" + id;
    }
}
```



```markdown
# 4.访问测试
```

正常参数访问：

![服务熔断的实现](SpringCloud微服务工具集.assets/服务熔断的实现.png)

错误参数访问：

![服务熔断的实现1](SpringCloud微服务工具集.assets/服务熔断的实现1.png)

<font color=red>频繁使用错误参数访问，触发熔断，再使用正常参数访问，但出现熔断现象</font>：

![服务熔断的实现2](SpringCloud微服务工具集.assets/服务熔断的实现2.png)

```markdown
# 5.总结
- 从上面演示过程中会发现，如果触发一定条件，断路器会自动打开，过了一点时间正常之后又会关闭。
```



```markdown
# 6.断路器打开条件
```

https://cloud.spring.io/spring-cloud-netflix/2.2.x/reference/html/#circuit-breaker-spring-cloud-circuit-breaker-with-hystrix

A service failure in the lower level of services can cause cascading failure all the way up to the user. When calls to a particular service exceed `circuitBreaker.requestVolumeThreshold` (default: 20 requests) and the failure percentage is greater than `circuitBreaker.errorThresholdPercentage` (default: >50%) in a rolling window defined by `metrics.rollingStats.timeInMilliseconds` (default: 10 seconds), the circuit opens and the call is not made. In cases of error and an open circuit, a fallback can be provided by the developer.

```markdown
# 打开关闭的条件：
- 1、当满足一定的阀值的时候（默认10秒内超过20个请求次数）
- 2、当失败率达到一定的时候（默认10秒内超过50%的请求失败）
- 3、到达以上阀值，断路器将会开启。当开启的时候，所有请求都不会进行转发
- 4、一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中【一个请求】进行转发。如果成功，断路器会关闭；若失败，继续开启。重复3和4。
```



```markdown
# 面试重点问题：断路器流程
```



![断路器流程](SpringCloud微服务工具集.assets/断路器流程.png)

<img src="SpringCloud微服务工具集.assets/断路器流程1.png" alt="断路器流程1" style="zoom:70%;" />

```markdown
# 7.默认的服务FallBack处理方法——defaultFallback
- 如果为每一个服务方法开发一个降级，可能会出现大量的代码的冗余，不利于维护，这个时候就需要加入【默认服务降级处理方法】
```

```java
package com.example.controller;

@RestController
@Slf4j
public class HystrixController {

    /**
     * 通过localhost:8080/FirstHystrix?id=xx的方式传递Id
     * HystrixCommand：指定熔断时快速返回方法；
     *                             该方法参数列表、返回值需与HystrixCommand修饰的方法一致
     *                             默认先执行自定义的备选处理
     * @return String
     */
    @GetMapping("FirstHystrix")
    @HystrixCommand(fallbackMethod = "testFallBack", defaultFallback = "defaultFallback")
    public String invokeFirstHystrix(Integer id) {
        log.info("============================");
        log.info("First Hystrix Demo !");
        if (id < 0) {
            throw new RuntimeException("无效Id！");
        }
        log.info("============================");
        return "First Hystrix demo is done !";
    }

    /**
     * 自定义备选处理
     * 一旦熔断后，不再运行throw new RuntimeException("无效Id！")，而运行testFallBack
     * @param id id
     * @return String
     */
    public String testFallBack(Integer id) {
        return "当前服务繁忙，请稍后再试！不合法Id：" + id;
    }

    /**
     * 使用Hystrix默认的备选处理
     */
    public String defaultFallback() {
        return "执行Hystrix默认的备选处理";
    }
}
```

## 9.6.OpenFeign整合Hystrix

OpenFeign默认依赖了hystrix，无需显式引入：

<img src="SpringCloud微服务工具集.assets/OpenFeign整合Hystrix1.png" alt="OpenFeign整合Hystrix1" style="zoom:50%;" />

创建`HystrixOpenfeign`项目（Module），调用`HystrixClient`服务：

```markdown
# 引入OpenFeign依赖
```

```xml
<dependencies>
    <!--SpringBootWeb-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--引入Consul-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>

    <!--引入健康检查依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!--Open Feign依赖-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

```markdown
# 加入注解，开启OpenFeign调用——@EnableFeignClients
```

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenzufeng
 * EnableDiscoveryClient：代表服务注册中心Consul客户端
 * EnableFeignClients：开启OpenFeogn客户端调用
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HystrixOpenFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixOpenFeignApplication.class, args);
    }
}
```

```markdown
# 创建feignclient包和HystrixOpenFeignClient接口，调用HystrixClient服务
```

```java
package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenzufeng
 */
@FeignClient("HystrixClient")
public interface HystrixOpenFeignClient {
    /**
     * 调用HystrixClient/HystrixController.invokeFirstHystrix服务
     * RequestParam("id")：底层数据传递方式Query String
     * @param id id
     * @return String
     */
    @GetMapping("FirstHystrix")
    String invokeFirstHystrix(@RequestParam("id") Integer id);
}
```

```markdown
# 在HystrixOpenfeignController注入OpenFeign客户端对象，并调用服务
- private HystrixOpenFeignClient hystrixOpenFeignClient;
- String invokeFirstHystrix = hystrixOpenFeignClient.invokeFirstHystrix(1);
```

```java
package com.example.controller;

@RestController
@Slf4j
public class HystrixOpenfeignController {
    @Autowired
    private HystrixOpenFeignClient hystrixOpenFeignClient;

    @GetMapping("HystrixOpenfeign")
    public String testHystrixOpenfeign() {
        log.info("调用HystrixClient/HystrixController.invokeFirstHystrix方法");
        String invokeFirstHystrix = hystrixOpenFeignClient.invokeFirstHystrix(1);
        log.info("HystrixClient/HystrixController.invokeFirstHystrix方法结果：{}", invokeFirstHystrix);
        return "调用HystrixClient/HystrixController.invokeFirstHystrix方法";
    }
}
```

```markdown
# 测试结果
```

`HystrixClient/HystrixController.java`

```java
package com.example.controller;

public class HystrixController {
    @GetMapping("FirstHystrix")
    @HystrixCommand(fallbackMethod = "testFallBack", defaultFallback = "defaultFallback")
    public String invokeFirstHystrix(Integer id) {
        log.info("============================");
        log.info("First Hystrix Demo !");
        if (id < 0) {
            throw new RuntimeException("无效Id！");
        }
        log.info("============================");
        return "First Hystrix demo is done !";
    }
//
}
```

![OpenFeign整合Hystrix](SpringCloud微服务工具集.assets/OpenFeign整合Hystrix.png)

## 9.7.服务降级的实现

```markdown
# 服务降级：站在系统整体负荷角度 
- 实现：关闭系统中某些边缘服务 保证系统核心服务运行
	Emps 核心服务   Depts 边缘服务

# 1.客户端openfeign + hystrix实现服务降级实现
- 引入hystrix依赖
- 配置文件开启feign支持hystrix
- 在feign客户端调用加入fallback指定降级处理
- 开发降级处理方法（服务调用失败默认处理的实现类）
```

```markdown
# 2.配置文件开启openfeign支持服务降级
```

```properties
server.port=8081
spring.application.name=HystrixOpenfeign

# 注册Consul服务的主机
spring.cloud.consul.host=localhost
# 注册Consul服务的端口号
spring.cloud.consul.port=8500

feign.hystrix.enabled=true #开启openfeign支持降级
```

```markdown
# 3.在openfeign客户端中加入Hystrix
```

```java
package com.example.feignclient;

@FeignClient(value = "PRODUCTS", fallback = ProductFallBack.class)
public interface ProductClient {
    @GetMapping("/product/hystrix")
    String testHystrix(@RequestParam("name") String name);
}
```



```markdown
# 4.开发fallback处理类
```

```java
package com.example.feignclient;

@Configuration
public class ProductFallBack implements ProductClient {
    @Override
    public String testHystrix(String name) {
        return "我是客户端的Hystrix服务实现!!!";
    }
}
```

![服务降级实现](SpringCloud微服务工具集.assets/服务降级实现.png)

## 9.8.Hystrix Dashboard(仪表盘)

```markdown
# 0.说明
- Hystrix Dashboard的一个主要优点是，它收集了关于每个HystrixCommand的一组度量。
- Hystrix仪表板以高效的方式【显示每个断路器的运行状况】。
```

![HystrixDashboard](SpringCloud微服务工具集.assets/HystrixDashboard.png)

```markdown
# 1.项目中引入依赖
```

```xml
<!--引入hystrix dashboard 依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

```markdown
# 2.入口类中开启hystrix dashboard
```

```java
@SpringBootApplication
@EnableHystrixDashboard //开启监控面板
public class Hystrixdashboard9990Application {
	public static void main(String[] args) {
		SpringApplication.run(Hystrixdashboard9990Application.class, args);
  }
}
```



```markdown
# 3.启动hystrix dashboard应用
- http://localhost:9990(dashboard端口)/hystrix
```

![HystrixDashboard1](SpringCloud微服务工具集.assets/HystrixDashboard1.png)

```markdown
# 4.监控的项目中入口类中加入监控路径配置[新版本坑],并启动监控项目
```

```java
@Bean
public ServletRegistrationBean getServlet() {
  HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
  ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
  registrationBean.setLoadOnStartup(1);
  registrationBean.addUrlMappings("/hystrix.stream");
  registrationBean.setName("HystrixMetricsStreamServlet");
  return registrationBean;
}
```



```markdown
# 5.通过监控界面监控
```

![HystrixDashboard2](SpringCloud微服务工具集.assets/HystrixDashboard2.png)

```markdown
# 6.点击监控,一致loading,打开控制台发现报错[特别坑]
```

![HystrixDashboard3](SpringCloud微服务工具集.assets/HystrixDashboard3.png)

```markdown
# 解决方案
- 新版本中springcloud将jquery版本升级为3.4.1，定位到monitor.ftlh文件中，js的写法如下：
	$(window).load(function() 
	
- jquery 3.4.1已经废弃上面写法

- 修改方案 修改monitor.ftlh为如下调用方式：
	$(window).on("load",function()
	
- 编译jar源文件，重新打包引入后，界面正常响应。
```

![HystrixDashboard4](SpringCloud微服务工具集.assets/HystrixDashboard4.png)

## 9.9.Hystrix停止维护

![Hystrix停止维护](SpringCloud微服务工具集.assets/Hystrix停止维护.png)

![Hystrix停止维护1](SpringCloud微服务工具集.assets/Hystrix停止维护1.png)

```markdown
# 官方地址:https://github.com/Netflix/Hystrix
- Hystrix（版本1.5.18）足够稳定，可以满足Netflix对我们现有应用的需求。同时，我们的重点已经转移到对应用程序的实时性能作出反应的更具适应性的实现，而不是预先配置的设置（例如，通过自适应并发限制）。
- 对于像Hystrix这样的东西有意义的情况，我们打算继续在现有的应用程序中使用Hystrix，并在新的内部项目中利用诸如【resilience4j】（实时自适应系统熔断）这样的开放和活跃的项目。
- sentinel：流量卫兵——流量控制、降级策略（根据异常）
- Dashboard也被废弃
```



# 10.Gateway组件使用

[Spring Cloud Gateway 2.2.3.RELEASE](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.3.RELEASE/reference/html/)

## 10.1.什么是服务网关

```markdown
# 1.说明
- 网关【统一服务入口路径——对所有服务进行集中管控】（前端Vue不可能一个个去直接调用后端集群服务的地址），可方便实现对平台众多【服务接口】进行管控
	对访问服务的【身份认证】、【防报文重发】与【防数据篡改】、功能调用的业务鉴权、响应数据的脱敏、流量与并发控制，甚至基于API调用的计量或者计费等等。

- 网关 =  路由转发 + 过滤器
	路由转发：接收一切外界请求，转发到后端的微服务上去；
	在服务网关中可以完成一系列的横切功能，例如权限校验、限流以及监控等，这些都可以通过过滤器完成
	
# 2.为什么需要网关
 - 网关可以实现服务的统一管理
 - 实现请求的路由转发、负载均衡
 - 过滤处理：对请求进行额外处理——网关可以解决微服务中通用代码的冗余问题（如权限控制、流量监控、限流等）
```

```markdown
# 3.网关组件在微服务中架构
```

<img src="SpringCloud微服务工具集.assets/Gateway组件.png" alt="Gateway组件" style="zoom:50%;" />



## 10.2.服务网关组件

### 10.2.1.zuul 1.x  2.x(netflix 组件)

Zuul is the front door for all requests from devices and web sites to the backend of the Netflix streaming application. As an edge service application, Zuul is built to enable dynamic routing, monitoring, resiliency and security.

```markdown
# 0.原文翻译
- https://github.com/Netflix/zuul/wiki
- zul是从设备和网站到Netflix流媒体应用程序后端的所有请求的前门。作为一个边缘服务应用程序，zul被构建为支持动态路由、监视、弹性和安全性。

# 1.zuul版本说明
- 目前zuul组件已经从1.0更新到2.0，但是作为SpringCloud官方不再推荐使用zuul2.0，但是依然支持zuul2。

# 2.springcloud 官方集成zuul文档
- https://cloud.spring.io/spring-cloud-netflix/2.2.x/reference/html/#netflix-zuul-starter
```

### 10.2.2.gateway (Spring)

This project provides a library for building an API Gateway on top of Spring MVC. Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.

```markdown
- https://spring.io/projects/spring-cloud-gateway
- 这个项目提供了一个在SpringMVC之上构建API网关的库。SpringCloud gateway旨在提供一种简单而有效的方法来路由到api，并为api提供横切关注点，比如：安全性、监控/度量和弹性。

# 1.特性
- 基于SpringBoot2.x 和 Spring webFlux（响应式异步非阻塞IO模型）和 Reactor 构建
- 动态路由
- 请求过滤
```



#### 10.2.2.1.开发网关动态路由

网关配置有两种方式：

- 一种是Java代码编写网关
- 一种是配置文件方式配置网关[推荐]



```markdown
# 1.创建SpringBoot项目，并引入网关依赖
	D:\Learning\SpringCloud\SpringCloudCode\SpringCloudDemo\SpringCloudDemoParent\Gateway
```

```xml
<!--引入gateway网关依赖
	去掉spring-boot-starter-web-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```



##### 快捷方式配置路由

```markdown
# 2.编写网关配置
	- Path= 可以写一个，也可以写多个；还支持通配符
	一般在Controller.java中添加总入口：@RequestMapping(“Category”)——- Path=/Category/**
```

```yml
server:
  port: 8080

spring:
  application:
    name: Gateway
  cloud:
    consul:
      port: 8500
      host: localhost
    gateway:
      routes:
        - id: category_router  # 指定路由唯一标识
          uri: http://localhost:8787 # OpenFeignCategoryApplication :8787/；指定路由服务的地址
          predicates:  # 断言
            - Path=/Category/**, /Product # 访问http://localhost:8080/Category/...，自动跳转http://localhost:8787/Category

        - id: product_router  # 指定路由唯一标识
            uri: http://localhost:8788 # OpenFeignProductApplication :8788/；指定路由服务的地址
            predicates: # 断言
              - Path=/Product/** # 访问http://localhost:8080/Product/...，自动跳转http://localhost:8788/Product
```

```markdown
# 3.启动Gateway网关项目
- 直接启动报错:
```

![启动Gateway网关项目](SpringCloud微服务工具集.assets/启动Gateway网关项目.png)

```markdown
- 在启动日志中发现，Gateway为了效率，使用webflux进行异步非阻塞模型的实现，因此和原来的web包冲突，去掉原来的web即可
```

![启动Gateway网关项目1](SpringCloud微服务工具集.assets/启动Gateway网关项目1.png)

```markdown
- 再次启动成功启动
```

<img src="SpringCloud微服务工具集.assets/启动Gateway网关项目2.png" alt="启动Gateway网关项目2" style="zoom:40%;" />

```markdown
# 4.测试网关路由转发
- 测试通过网关访问商品服务：http://localhost:8080/Product
	c.example.controller.ProductController   : ======================
	c.example.controller.ProductController   : 进入商品服务......
	c.example.controller.ProductController   : 提供当前服务的端口[8788]
	c.example.controller.ProductController   : ======================
```



##### Java方式配置路由

```java
package com.example.config

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order_route", r -> r.path("/order/**")
                        .uri("http://localhost:9997"))
                .build();
    }
}
```



#### 10.2.2.2.查看网关路由规则列表

```markdown
# 1.说明
- Gateway提供路由访问规则列表的web界面，但是默认是关闭的，如果想要查看服务路由规则可以在配置文件中开启
```

```yml
management:
  endpoints:
    web:
      exposure:
        include: "*"   #开启所有web端点暴露
```

```markdown
- 访问路由管理列表地址
- http://localhost:8989/actuator/gateway/routes
```

![查看网关路由规则列表](SpringCloud微服务工具集.assets/查看网关路由规则列表.png)



#### 10.2.2.3.配置路由服务负载均衡

```markdown
# 1.说明
- 现有路由配置方式，都是基于服务地址写死的路由转发
	uri: http://localhost:8787 # OpenFeignCategoryApplication :8787/；指定路由服务的地址

- 能不能根据服务名称进行路由转发，同时实现负载均衡的呢?
	引入的Consul依赖添加了Ribbon依赖
```

<img src="SpringCloud微服务工具集.assets/配置路由服务负载均衡.png" alt="配置路由服务负载均衡" style="zoom:50%;" />

```markdown
# 2.动态路由以及负载均衡转发配置
```

```yml
server:
  port: 8080

spring:
  application:
    name: Gateway
  cloud:
    consul:
      port: 8500
      host: localhost
    gateway:
      routes:
        - id: category_router  # 指定路由唯一标识
          uri: http://localhost:8787 # OpenFeignCategoryApplication :8787/；指定路由服务的地址
          predicates:  # 断言
            - Path=/Category/**, /... # 访问http://localhost:8080/Category/...，自动跳转http://localhost:8787/Category

        - id: product_router  # 指定路由唯一标识
          # uri: http://localhost:8788 # OpenFeignProductApplication :8788/；指定路由服务的地址
          uri: lb://OpenFeignProduct # lb代表转发后台服务使用负载均衡，OpenFeignProduct代表服务注册中心上的服务名
          predicates: # 断言
            - Path=/Product/**      
```





#### 10.2.2.4.常用路由predicate(断言、验证)

网关 = 断言（前置Filter——Gateway Web Handler $\rightarrow$ Proxied Service） + 

​			  过滤（后置Filter——Proxied Service $\rightarrow$ Gateway Web Handler）

断言：当请求到达网关时，网关前置处理

过滤：当请求满足断言的所有条件后，会向后端服务转发，在向后端服务转发之前会经过一系列过滤

![img](SpringCloud微服务工具集.assets/spring_cloud_gateway_diagram.png)

```markdown
# 1.Gateway支持多种方式的predicate
```

![Gateway支持多种方式的predicate](SpringCloud微服务工具集.assets/Gateway支持多种方式的predicate.png)

```markdown
- After=2020-07-21T11:33:33.993+08:00[Asia/Shanghai]  	  `指定日期之后的请求进行路由
- Before=2020-07-21T11:33:33.993+08:00[Asia/Shanghai]       `指定日期之前的请求进行路由
- Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
- Cookie=username, chenyn							`基于指定cookie的请求进行路由
- Cookie=username, [A-Za-z0-9]+						  `基于指定cookie的请求进行路由	
	`curl http://localhost:8989/user/findAll --cookie "username=zhangsna" 使用curl命令进行验证
- Header=X-Request-Id, \d+			 `基于请求头中的指定属性的正则匹配路由(这里全是整数)
	`curl http://localhost:8989/user/findAll -H "X-Request-Id:11"
- Method=GET, POST				   `基于指定的请求方式请求进行路由

- 官方更多: https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.3.RELEASE/reference/html/#the-cookie-route-predicate-factory
```

```markdown
# 2.使用predicate
```

```yml
spring:
  application:
    name: gateway
  cloud:
    consul:
      host: localhost
      port: 8500
    gateway:
      routes:
        - id: user_route
          #uri: http://localhost:9999/
          uri: lb://users
          predicates:
            - Path=/user/**
            - After=2020-07-21T11:39:33.993+08:00[Asia/Shanghai]
            - Cookie=username,[A-Za-z0-9]+
            -  Header=X-Request-Id, \d+
```





#### 10.2.2.5.常用的Filter以及自定义filter

Route filters allow the modification of the incoming HTTP request or outgoing HTTP response in some manner. Route filters are scoped to a particular route. Spring Cloud Gateway includes many built-in GatewayFilter Factories.

```markdown
# 1.原文翻译
- 官网: 
	https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.3.RELEASE/reference/html/#gatewayfilter-factories
	
- 路由过滤器允许以某种方式修改传入的HTTP请求或传出的HTTP响应。路由筛选器的作用域是特定路由。SpringCloudGateway包括许多内置的GatewayFilter工厂。

# 2.作用
- 当我们有很多个服务时，比如下图中的user-service、order-service、product-service等服务，客户端请求各个服务的Api时，每个服务都需要做相同的事情，比如鉴权、限流、日志输出等。
```

![Filter](SpringCloud微服务工具集.assets/Filter.png)

![Filter1](SpringCloud微服务工具集.assets/Filter1.png)

```markdown
# 2.使用内置过滤器
```

![Filter2](SpringCloud微服务工具集.assets/Filter2.png)

```markdown
- AddRequestHeader=X-Request-red, blue			 `给路由对象的所有转发请求，增加请求头的filter`
- AddRequestParameter=red, blue					`给路由对象的所有转发请求增加请求参数的filterr`
- AddResponseHeader=X-Response-Red, AAA		      `给路由对象的所有转发请求增加响应头filter`
- PrefixPath=/emp							    `给路由对象的所有转发请求url增加前缀的filter`
- StripPrefix=2								     `去掉前缀的filter`
```

```properties
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
    gateway:
      routes:
        - id: product_router #路由对象唯一标识
          #uri: http://localhost:8788 #用来类别服务地址  http://localhost:8788/list
          uri: lb://PRODUCT   #实现请求负载均衡处理 /product/product/list
          predicates:  #断言 用来配置路由规则
            - Path=/product/**
            #- After=2021-04-20T10:20:22.124+08:00[Asia/Shanghai]
            #- Before=2021-04-20T10:23:22.124+08:00[Asia/Shanghai]
            #- Between=2021-04-20T10:23:22.124+08:00[Asia/Shanghai],2021-04-20T10:25:22.124+08:00[Asia/Shanghai]
            #- Cookie=name,chenyn
            # - Cookie=name,[A-Za-z0-9]+
            #- Header=X-Request-Id,\d+
            #- Method=GET
          filters:
            - AddRequestHeader=User-Name, chenyn
            - AddRequestParameter=color, blue
            - AddResponseHeader=X-Response-Red, Blue
            #- PrefixPath=/product #加入指定前缀filter
            - StripPrefix=1 #去掉请求路径中n级前缀
```



```markdown
# 3.使用自定义filter（全局filter）
```

```java
@Configuration
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入自定义的filter");
        if(exchange.getRequest().getQueryParams().get("username")!=null){
            log.info("用户身份信息合法,放行请求继续执行!!!");
            return chain.filter(exchange);
        }
        log.info("非法用户,拒绝访问!!!");
       return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
```



# 11.Config组件使用

## 什么是Config

```markdown
# 0.说明
- https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.2.3.RELEASE/reference/html/#_spring_cloud_config_server

- config(配置)又称为【统一配置中心】
	顾名思义，就是将配置统一管理，配置统一管理的好处是，在日后大规模集群部署服务应用时，相同的服务配置一致，日后再修改配置只需要统一修改，全部同步，不需要一个一个服务手动维护。
```

```markdown
# 1.统一配置中心组件流程图
```



![统一配置中心组件流程图](SpringCloud微服务工具集.assets/统一配置中心组件流程图.png)

## Config Server 开发

```markdown
# 1.引入依赖
```

```xml
<!--引入统一配置中心-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

```markdown
# 2.开启统一配置中心服务
```

```java
@SpringBootApplication
@EnableConfigServer
public class Configserver7878Application {
	public static void main(String[] args) {
		SpringApplication.run(Configserver7878Application.class, args);
	}
}
```

```markdown
# 3.修改配置文件
```

```properties
server.port=7878
spring.application.name=configserver
spring.cloud.consul.host=localhost
spring.cloud.consul.port=8500
```

```markdown
# 4.直接启动服务报错
-  没有指定远程仓库的相关配置
```

![Config组件](SpringCloud微服务工具集.assets/Config组件.png)

```markdown
# 5.创建远程仓库
- github创建一个仓库
```

![Config组件1](SpringCloud微服务工具集.assets/Config组件1.png)

```markdown
# 6.复制仓库地址
- https://github.com/chenyn-java/configservers.git
```

![Config组件2](SpringCloud微服务工具集.assets/Config组件2.png)

```markdown
# 7.在统一配置中心服务中修改配置文件指向远程仓库地址
```

```properties
spring.cloud.config.server.git.uri=https://github.com/chenyn-java/configservers.git   指定仓库的url
spring.cloud.config.server.git.default-label=master							    指定访问的分支
#spring.cloud.config.server.git.username=      私有仓库访问用户名
#spring.cloud.config.server.git.password=	私有仓库访问密码
```

```markdown
# 8.再次启动统一配置中心
```

![Config组件3](SpringCloud微服务工具集.assets/Config组件3.png)

```markdown
# 9.拉取远端配置 [三种方式][]
- 1. http://localhost:7878/test-xxxx.properties
- 2. http://localhost:7878/test-xxxx.json
- 3. http://localhost:7878/test-xxxx.yml
```

![Config组件4](SpringCloud微服务工具集.assets/Config组件4.png)

```markdown
# 10.拉取远端配置规则
- label/name-profiles.yml|properties|json
	`label   代表去那个分支获取 默认使用master分支
	`name    代表读取那个具体的配置文件文件名称
	`profile 代表读取配置文件环境
```

![image-20200722105313716](SpringCloud微服务工具集.assets/image-20200722105313716.png)

```markdown
# 11.查看拉取配置详细信息
- http://localhost:7878/client/dev       [client:代表远端配置名称][dev:代表远程配置的环境]
```

![Config组件5](SpringCloud微服务工具集.assets/Config组件5.png)

```markdown
# 12.指定分支和本地仓库位置
```

```properties
spring.cloud.config.server.git.basedir=/localresp 		#一定要是一个空目录,在首次会将该目录清空
spring.cloud.config.server.git.default-label=master
```

## Config Client 开发

```markdown
# 1.项目中引入config client依赖
```

```xml
<!--引入config client-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

```markdown
# 2.编写配置文件
```

```properties
spring.cloud.config.discovery.enabled=true                    # 开启统一配置中心服务
spring.cloud.config.discovery.service-id=configserver   # 指定统一配置服务中心的服务唯一标识
spring.cloud.config.label=master				   # 指定从仓库的那个分支拉取配置	
spring.cloud.config.name=client					   # 指定拉取配置文件的名称
spring.cloud.config.profile=dev					    # 指定拉取配置文件的环境
```

```markdown
# 3.远程仓库创建配置文件
- client.properties								[用来存放公共配置][]
	spring.application.name=configclient
	spring.cloud.consul.host=localhost
	spring.cloud.consul.port=8500

- client-dev.properties  			[用来存放研发相关配置][注意:这里端口为例,以后不同配置分别存放]
	server.port=9099

- client-prod.properties			[用来存放生产相关配置][]
	server.port=9098
```

![Config组件6](SpringCloud微服务工具集.assets/Config组件6.png)

```markdown
# 4.启动客户端服务进行远程配置拉取测试
- 直接启动过程中发现无法启动直接报错
```

![image-20200722102851999](SpringCloud微服务工具集.assets/image-20200722102851999.png)![image-20200722102901146](SpringCloud微服务工具集.assets/image-20200722102901146.png)

```markdown
# 报错原因
- 项目中目前使用的是application.properties启动项目,使用这个配置文件在springboot项目启动过程中不会等待远程配置拉取,直接根据配置文件中内容启动,因此当需要注册中心,服务端口等信息时,远程配置还没有拉取到,所以直接报错
```

![image-20200722103435260](SpringCloud微服务工具集.assets/image-20200722103435260.png)

```markdown
# 解决方案
- 应该在项目启动时先等待拉取远程配置,拉取远程配置成功之后再根据远程配置信息启动即可,为了完成上述要求springboot官方提供了一种解决方案,就是在使用统一配置中心时应该将微服务的配置文件名修改为bootstrap.(properties|yml),bootstrap.properties作为配置启动项目时,会优先拉取远程配置,远程配置拉取成功之后根据远程配置启动当前应用。
```

![image-20200722103823678](SpringCloud微服务工具集.assets/image-20200722103823678.png)

```markdown
# 再次启动服务
```

![image-20200722103913142](SpringCloud微服务工具集.assets/image-20200722103913142.png)

![image-20200722104031932](SpringCloud微服务工具集.assets/image-20200722104031932.png)

-----

## 手动配置刷新

```markdown
# 1.说明
- 在生产环境中,微服务可能非常多,每次修改完远端配置之后,不可能对所有服务进行重新启动,这个时候需要让修改配置的服务能够刷新远端修改之后的配置,从而不要每次重启服务才能生效,进一步提高微服务系统的维护效率。在springcloud中也为我们提供了手动刷新配置和自动刷新配置两种策略,这里我们先使用手动配置文件刷新。

# 2.在config client端加入刷新暴露端点
```

```properties
management.endpoints.web.exposure.include=*          #开启所有web端点暴露
```

![image-20200722144351017](SpringCloud微服务工具集.assets/image-20200722144351017.png)

```markdown
# 3.在需要刷新代码的类中加入刷新配置的注解
```

```java
@RestController
@RefreshScope
@Slf4j
public class TestController {
    @Value("${name}")
    private String name;
    @GetMapping("/test/test")
    public String test(){
      log.info("当前加载配置文件信息为:[{}]",name);
      return name;
    }
}
```

![image-20200722153537692](SpringCloud微服务工具集.assets/image-20200722153537692.png)

```markdown
# 4.在远程配置中加入name并启动测试
```

![image-20200722153731602](SpringCloud微服务工具集.assets/image-20200722153731602.png)

```markdown
# 5.启动之后直接访问
```

![image-20200722153806932](SpringCloud微服务工具集.assets/image-20200722153806932.png)

```markdown
# 6.修改远程配置
```

![image-20200722203225968](SpringCloud微服务工具集.assets/image-20200722203225968.png)

```markdown
# 7.修改之后在访问
- 发现并没有自动刷新配置?
- 必须调用刷新配置接口才能刷新配置
```

![image-20200722203317795](SpringCloud微服务工具集.assets/image-20200722203317795.png)

```markdown
# 8.手动调用刷新配置接口
- curl -X POST http://localhost:9099/actuator/refresh
```

![image-20200722203417879](SpringCloud微服务工具集.assets/image-20200722203417879.png)

```markdown
# 9.在次访问发现配置已经成功刷新
```

![image-20200722203452506](SpringCloud微服务工具集.assets/image-20200722203452506.png)

-----

# 12.Bus组件的使用

## 什么是Bus (AMQP RibbitMQ、Kafka）

Spring Cloud Bus links nodes of a distributed system with a lightweight message broker. This can then be used to broadcast state changes (e.g. configuration changes) or other management instructions. AMQP and Kafka broker implementations are included with the project. Alternatively, any [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream) binder found on the classpath will work out of the box as a transport. 

```markdown
# 0.翻译
- https://spring.io/projects/spring-cloud-bus
- SpringCloudBus使用轻量级消息代理将分布式系统的节点连接起来。然后，可以使用它来广播状态更改（例如配置更改）或其他管理指令。AMQP和Kafka broker(中间件)实现包含在项目中。或者，在类路径上找到的任何SpringCloudStream绑定器都可以作为传输使用。

- 通俗定义: bus称之为springcloud中消息总线，主要用来在微服务系统中实现远端配置更新时通过广播形式通知所有客户端刷新配置信息，避免手动重启服务的工作
```

## 实现配置刷新原理

![image-20200723150335451](SpringCloud微服务工具集.assets/image-20200723150335451.png)

## 搭建RabbitMQ服务

```markdown
# 0.下载rabbitmq安装包 [][可以直接使用docker安装更方便]
- 官方安装包下载:https://www.rabbitmq.com/install-rpm.html#downloads
[注意:][这里安装包只能用于centos7.x系统]
```

![image-20190925220343521](SpringCloud微服务工具集.assets/image-20190925220343521.png)

```markdown

# 1.将rabbitmq安装包上传到linux系统中
	erlang-22.0.7-1.el7.x86_64.rpm
	rabbitmq-server-3.7.18-1.el7.noarch.rpm

# 2.安装Erlang依赖包
	rpm -ivh erlang-22.0.7-1.el7.x86_64.rpm

# 3.安装RabbitMQ安装包(需要联网)
	yum install -y rabbitmq-server-3.7.18-1.el7.noarch.rpm
		注意:默认安装完成后配置文件模板在:/usr/share/doc/rabbitmq-server-3.7.18/rabbitmq.config.example目录中,需要	
				将配置文件复制到/etc/rabbitmq/目录中,并修改名称为rabbitmq.config
# 4.复制配置文件
	cp /usr/share/doc/rabbitmq-server-3.7.18/rabbitmq.config.example /etc/rabbitmq/rabbitmq.config

# 5.查看配置文件位置
	ls /etc/rabbitmq/rabbitmq.config

# 6.修改配置文件(参见下图:)
	vim /etc/rabbitmq/rabbitmq.config 
```

![image-20190925222230260](SpringCloud微服务工具集.assets/image-20190925222230260-3836271.png)

将上图中配置文件中红色部分去掉`%%`,以及最后的`,`逗号 修改为下图:

![image-20190925222329200](SpringCloud微服务工具集.assets/image-20190925222329200-3836312.png)

```markdown
# 7.执行如下命令,启动rabbitmq中的插件管理
	rabbitmq-plugins enable rabbitmq_management
	
	出现如下说明:
		Enabling plugins on node rabbit@localhost:
    rabbitmq_management
    The following plugins have been configured:
      rabbitmq_management
      rabbitmq_management_agent
      rabbitmq_web_dispatch
    Applying plugin configuration to rabbit@localhost...
    The following plugins have been enabled:
      rabbitmq_management
      rabbitmq_management_agent
      rabbitmq_web_dispatch

    set 3 plugins.
    Offline change; changes will take effect at broker restart.

# 8.启动RabbitMQ的服务
	systemctl start rabbitmq-server
	systemctl restart rabbitmq-server
	systemctl stop rabbitmq-server
	

# 9.查看服务状态(见下图:)
	systemctl status rabbitmq-server
  ● rabbitmq-server.service - RabbitMQ broker
     Loaded: loaded (/usr/lib/systemd/system/rabbitmq-server.service; disabled; vendor preset: disabled)
     Active: active (running) since 三 2019-09-25 22:26:35 CST; 7s ago
   Main PID: 2904 (beam.smp)
     Status: "Initialized"
     CGroup: /system.slice/rabbitmq-server.service
             ├─2904 /usr/lib64/erlang/erts-10.4.4/bin/beam.smp -W w -A 64 -MBas ageffcbf -MHas ageffcbf -
             MBlmbcs...
             ├─3220 erl_child_setup 32768
             ├─3243 inet_gethost 4
             └─3244 inet_gethost 4
      .........
# 10.启动出现如下错误:
- 4月 21 10:10:50 bogon systemd[1]: Starting RabbitMQ broker...
- 4月 21 10:11:11 bogon rabbitmq-server[1772]: ERROR: epmd error for host bogon: address (cannot connect to host/port)
- 4月 21 10:11:11 bogon systemd[1]: rabbitmq-server.service: main process exited, code=exited, status=1/FAILURE
 `解决方案`: 
   1. 修改主机名   vim /etc/hostname   修改为自己注解名  rabbimq   2.修改完必须重启
   3. vim /etc/hosts   在文件中添加:  127.0.0.1   自己主机名(rabbitmq)
```

![image-20190925222743776](SpringCloud微服务工具集.assets/image-20190925222743776-3836511.png)

```markdown
# 10.关闭防火墙服务
	systemctl disable firewalld
    Removed symlink /etc/systemd/system/multi-user.target.wants/firewalld.service.
    Removed symlink /etc/systemd/system/dbus-org.fedoraproject.FirewallD1.service.
	systemctl stop firewalld   

# 11.访问web管理界面
	http://10.15.0.8:15672/
```

 ![image-20190926194738708](SpringCloud微服务工具集.assets/image-20190926194738708-3836601.png)

```markdown
# 12.登录管理界面
	username:  guest
	password:  guest
```

![image-20190926194954822](SpringCloud微服务工具集.assets/image-20190926194954822-3836665.png)

```markdown
# 13.MQ服务搭建成功
```

## 实现自动配置刷新

```markdown
# 1.在所有项目中引入bus依赖
```

```xml
<!--引入bus依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```



```markdown
# 2.配置统一配置中心连接到mq
```

```properties
spring.rabbitmq.host=localhost							# 连接主机
spring.rabbitmq.port=5672							    # 连接mq端口
spring.rabbitmq.username=user						     # 连接mq用户名
spring.rabbitmq.password=password					      # 连接mq密码
```

```markdown
# 3.远端配置中加入连接mq配置
```

![image-20200723105645915](SpringCloud微服务工具集.assets/image-20200723105645915.png)

```markdown
# 4.启动统一配置中心服务
- 正常启动
```

![image-20200723111220198](SpringCloud微服务工具集.assets/image-20200723111220198.png)

```markdown
# 5.启动客户端服务
- 加入bus组件之后客户端启动报错
- 原因springcloud中默认链接不到远程服务器不会报错,但是在使用bus消息总线时必须开启连接远程服务失败报错
```

![image-20200723111312496](SpringCloud微服务工具集.assets/image-20200723111312496.png)

```properties
spring.cloud.config.fail-fast=true
```

![image-20200723111754187](SpringCloud微服务工具集.assets/image-20200723111754187.png)

```markdown
# 6.修改远程配置后在配置中心服务通过执行post接口刷新配置
- curl -X POST http://localhost:7878/actuator/bus-refresh
```

![image-20200723112316476](SpringCloud微服务工具集.assets/image-20200723112316476.png)

```markdown
# 7.通过上述配置就实现了配置统一刷新
```

## 指定服务刷新配置

```markdown
# 1.说明
- 默认情况下使用curl -X POST http://localhost:7878/actuator/bus-refresh这种方式刷新配置是全部广播形式,也就是所有的微服务都能接收到刷新配置通知,但有时我们修改的仅仅是某个服务的配置,这个时候对于其他服务的通知是多余的,因此就需要指定服务进行通知

# 2.指定服务刷新配置实现
- 指定端口刷新某个具体服务: curl -X POST http://localhost:7878/actuator/bus-refresh/configclient:9090
- 指定服务id刷新服务集群节点: curl -X POST http://localhost:7878/actuator/bus-refresh/configclient
 	[注意:][configclient代表刷新服务的唯一标识]
```

## 集成webhook实现自动刷新

```markdown
# 1.配置webhooks
- 说明: git仓库提供一种特有机制: 这种机制就是一个监听机制    监听就是仓库提交事件 ...  触发对应事件执行
- javascript: 事件  事件源 html标签  事件: 触发特定动作click  ...  事件处理程序:函数
- 添加webhooks
- 在webhooks中添加刷新配置接口

- 内网穿透的网站: https://natapp.cn/
```

![image-20200723120419412](SpringCloud微服务工具集.assets/image-20200723120419412.png)

![image-20200723120947229](SpringCloud微服务工具集.assets/image-20200723120947229.png)

```markdown
# 2.解决400错误问题
- 在配置中心服务端加入过滤器进行解决(springcloud中一个坑)
```

```java
@Component
public class UrlFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
 
        String url = new String(httpServletRequest.getRequestURI());
 
        //只过滤/actuator/bus-refresh请求
        if (!url.endsWith("/bus-refresh")) {
            chain.doFilter(request, response);
            return;
        }
 
        //获取原始的body
        String body = readAsChars(httpServletRequest);
 
        System.out.println("original body:   "+ body);
 
        //使用HttpServletRequest包装原始请求达到修改post请求中body内容的目的
        CustometRequestWrapper requestWrapper = new CustometRequestWrapper(httpServletRequest);
 
        chain.doFilter(requestWrapper, response);
 
    }
 
    @Override
    public void destroy() {
 
    }
 
    private class CustometRequestWrapper extends HttpServletRequestWrapper {
        public CustometRequestWrapper(HttpServletRequest request) {
            super(request);
        }
 
        @Override
        public ServletInputStream getInputStream() throws IOException {
            byte[] bytes = new byte[0];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
 
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.read() == -1 ? true:false;
                }
 
                @Override
                public boolean isReady() {
                    return false;
                }
 
                @Override
                public void setReadListener(ReadListener readListener) {
 
                }
 
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
 
    public static String readAsChars(HttpServletRequest request)
    {
 
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
```

![image-20200723121203864](SpringCloud微服务工具集.assets/image-20200723121203864.png)

-----

# 13. SpringCloud微服务工具集总结

```markdown
# 服务间通信方式：RPC、Http协议 (SpringCloud中)

# 1.服务注册中心组件：Eureka、Consul

# 2.服务间通信实现：
	 a. RestTemplate(HttpClient对象) + Ribbon组件(SpringCloud)
	 b. openfegin(伪httpclient客户端组件 底层默认集成Ribbon)  推荐

# 3.微服务保护组件: Hystrix (防止服务雪崩现象)  Hystrix DashBoard 组件  维护状态

# 4.微服务网关组件: Zuul1.x  Zuul2.x(netflix组件)、Gateway(Spring 组件)
	网关： 路由转发  +  过滤器（前置predicate   后置filter）

# 5.统一配置中心组件:  Config (netflix)
	作用: 用来将微服务中所有配置进行远程git仓库统一管理

# 6.消息总线:         Bus 
	作用: 用来通过消息中间件将所有微服务连接到一起，利用广播模型实现配置自动刷新机制
```