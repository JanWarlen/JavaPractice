package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ApiAsyncConsumerForCompletableFuture {
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
        RpcContext.getContext().setAttachment("company", "Completable");

        CompletableFuture<String> completableFuture = RpcContext.getContext().getCompletableFuture();
        completableFuture.whenComplete((v, t) -> {
            if (Objects.nonNull(t)) {
                t.printStackTrace();
            } else {
                System.out.println(System.currentTimeMillis());
                System.out.println(v);
            }
        });
        System.out.println("finish");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
