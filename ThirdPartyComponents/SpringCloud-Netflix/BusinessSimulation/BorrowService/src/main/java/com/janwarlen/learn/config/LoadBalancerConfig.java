package com.janwarlen.learn.config;

public class LoadBalancerConfig {
    // LoadBalancer 指定负载均衡策略，在2020版本之后可用，2020之前使用ribbon
    /*@Bean
    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }*/

}
