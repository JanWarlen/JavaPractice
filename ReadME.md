# 简单介绍
此仓库存储各项入门or脚手架程序
## Basic
主要囊括JDK相关内容，如语法、API等
## ThirdPartyComponents
主要囊括Java生态圈中一些中间件/框架的入门使用or脚手架
### Curator
Zookeeper操作客户端api验证，主要是[Curator's Tech Notes 翻译与部分验证](http://www.janwarlen.com/2018/02/26/Curator's%20Tech%20Notes/)中的实验代码
### DynamicLogger
实验程序运行期间动态添加日志Appender，Log4j
#### BaseJar
日志appender动态添加核心代码
#### DynamicLoggerDemo
打印日志演示程序
### Elastic-job
Elastic-job使用入门程序
### MockitoAndPowerMock
单元测试相关，mockito 和 powermock 使用入门代码
### MongoTemplate
MongoTemplate使用入门代码，结合SpringBoot
### Netty
Netty学习中练习代码
### Redis
SpringBoot整合Redis代码，主要包含
- Redis的多模式兼容
- 分布式锁的简单实现（setnx 时间戳）
### RocketMQ
SpringBoot整合RocketMQ入门代码
### SpringBootDateFormat
SpringBoot日期自动处理代码
### SpringBootV1
SpringBoot 1.x版本的HelloWorld代码
### TransformClasses
javaAgent结合类增强（javassist和asm）入门代码
### TransmittableThreadLocal
TransmittableThreadLocal使用入门代码
### WebSocket
WebSocket使用Netty实现的入门代码
### SpringCloud-Netflix
SpringCloud第一代实现Netflix的入门代码
#### Eureka-Server
服务注册中心（Register Service）：它是一个 Eureka Server，用于提供服务注册和发现功能。  
一般一个公司只会有一个Eureka-Server(集群)，因此算是SpringCloud的生态基础组件
#### BusinessSimulation
模拟业务代码，扮演服务提供者（Provider Service）和服务消费者（Consumer Service），主要是将自身的业务功能注册到`Eureka-Server`中供其他业务使用  
可以既是服务提供者也是服务消费者
#### SpringCloudConfigCenter
配置中心，可以结合git管理配置
#### SpringCloudGateway
网关
### SpringCloud-Alibaba
SpringCloud第二代实现Alibaba的入门代码
其中服务注册中心和配置管理中心使用nacos代替，无须额外工程
流量控制使用sentinel管理
#### BusinessSimulations
模拟用的业务代码
### XXL-JOB
XXL-JOB入门demo，需要额外搭建xxl-job-admin（依赖MySQL）

