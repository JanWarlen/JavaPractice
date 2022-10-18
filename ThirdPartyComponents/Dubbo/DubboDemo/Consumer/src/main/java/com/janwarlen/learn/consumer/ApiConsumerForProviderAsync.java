package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingServiceAsync;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ApiConsumerForProviderAsync {
    public static void main(String[] args) {
        ReferenceConfig<GreetingServiceAsync> greetingServiceReferenceConfig = new ReferenceConfig<>();
        greetingServiceReferenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        greetingServiceReferenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        greetingServiceReferenceConfig.setInterface(GreetingServiceAsync.class);
        greetingServiceReferenceConfig.setVersion("1.0.0");
        greetingServiceReferenceConfig.setGroup("dubbo");
        greetingServiceReferenceConfig.setTimeout(3_000);

        greetingServiceReferenceConfig.setAsync(true);
        GreetingServiceAsync greetingServiceAsync = greetingServiceReferenceConfig.get();
        System.out.println(System.currentTimeMillis());
        CompletableFuture<String> demo = greetingServiceAsync.sayHello("demo");
        demo.whenComplete((v,t) -> {
            if (Objects.nonNull(t)) {
                t.printStackTrace();
            } else {
                System.out.println(System.currentTimeMillis());
                System.out.println(v);
            }
        });
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
