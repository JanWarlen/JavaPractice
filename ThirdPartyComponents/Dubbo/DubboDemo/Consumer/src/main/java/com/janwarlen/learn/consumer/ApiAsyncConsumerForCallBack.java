package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.remoting.exchange.ResponseCallback;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.dubbo.FutureAdapter;

public class ApiAsyncConsumerForCallBack {
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
        RpcContext.getContext().setAttachment("company", "callback");

        ((FutureAdapter<?>) RpcContext.getContext().getFuture()).getFuture().setCallback(new ResponseCallback() {
            @Override
            public void done(Object response) {
                System.out.println(System.currentTimeMillis());
                System.out.println("done res:" + response);
            }

            @Override
            public void caught(Throwable exception) {
                System.out.println("error:" + exception.getMessage());
            }
        });
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
