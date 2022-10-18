package com.janwarlen.learn.provider;

import com.janwarlen.learn.api.GreetingServiceRpcContext;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;
import java.util.HashMap;

public class ApiProviderForAsyncContext {
    public static void main(String[] args) {
        ServiceConfig<GreetingServiceRpcContext> serviceConfig = new ServiceConfig<GreetingServiceRpcContext>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        serviceConfig.setInterface(GreetingServiceRpcContext.class);
        serviceConfig.setRef(new GreetingServiceAsyncContextImpl());

        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo");

        //设置线程池策略
        /*HashMap<String, String> parameters = new HashMap<>();
        parameters.put("threadpool", "mythreadpool");
        serviceConfig.setParameters(parameters);*/

        serviceConfig.export();

        System.out.println("server is started");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
