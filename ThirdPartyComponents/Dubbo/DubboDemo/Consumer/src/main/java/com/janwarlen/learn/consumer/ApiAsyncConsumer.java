package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ApiAsyncConsumer {
    public static void main(String[] args) {
        ReferenceConfig<GreetingService> greetingServiceReferenceConfig = new ReferenceConfig<>();
        greetingServiceReferenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        greetingServiceReferenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        greetingServiceReferenceConfig.setInterface(GreetingService.class);
        greetingServiceReferenceConfig.setVersion("1.0.0");
        greetingServiceReferenceConfig.setGroup("dubbo");
        greetingServiceReferenceConfig.setTimeout(2_000);

        greetingServiceReferenceConfig.setAsync(true);
        GreetingService greetingService = greetingServiceReferenceConfig.get();
        System.out.println(System.currentTimeMillis());
        System.out.println("return im:" + greetingService.sayHello("demo"));

        Future<String> future = RpcContext.getContext().getFuture();
        try {
            System.out.println(System.currentTimeMillis());
            // 依旧会阻塞至结果返回
            System.out.println("future.get:" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
