package com.janwarlen.learn.provider;

import com.janwarlen.learn.api.GreetingService;
import com.janwarlen.learn.api.GreetingServiceAsync;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

public class ApiProviderForAsync {
    public static void main(String[] args) {
        ServiceConfig<GreetingServiceAsync> greetingServiceServiceConfig = new ServiceConfig<>();
        greetingServiceServiceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://192.168.125.201:2181");
        greetingServiceServiceConfig.setRegistry(registryConfig);
        greetingServiceServiceConfig.setInterface(GreetingServiceAsync.class);
        greetingServiceServiceConfig.setRef(new GreetingServiceAsyncImpl());
        greetingServiceServiceConfig.setVersion("1.0.0");
        greetingServiceServiceConfig.setGroup("dubbo");
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
