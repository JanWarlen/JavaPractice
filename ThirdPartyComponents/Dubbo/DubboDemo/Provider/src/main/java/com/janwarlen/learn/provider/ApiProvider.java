package com.janwarlen.learn.provider;

import com.janwarlen.learn.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;
import java.util.HashMap;

public class ApiProvider {
    public static void main(String[] args) {
        ServiceConfig<GreetingService> greetingServiceServiceConfig = new ServiceConfig<>();
        // 设置应用程序配置
        greetingServiceServiceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        // 设置服务注册中心信息
//        RegistryConfig registryConfig = new RegistryConfig("zookeeper://192.168.125.201:2181");
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        greetingServiceServiceConfig.setRegistry(registryConfig);
        // 设置接口与实现类
        greetingServiceServiceConfig.setInterface(GreetingService.class);
        greetingServiceServiceConfig.setRef(new GreetingServiceImpl());
        // 设置服务分组与版本
        greetingServiceServiceConfig.setVersion("1.0.0");
        greetingServiceServiceConfig.setGroup("dubbo");
        // 设置线程池策略
        /*HashMap<String, String> params = new HashMap<>();
        params.put("threadpool", "mythreadpool");
        greetingServiceServiceConfig.setParameters(params);*/

        // 导出服务
        // 启动NettyServer 监听链接请求，注册服务到注册中心zk
        greetingServiceServiceConfig.export();
        System.out.println("start");
        try {
            // 线程挂起，避免服务停止
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
