package com.janwarlen.learn.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    // 启用负载均衡，不启用无法根据服务名互相访问
    @LoadBalanced
    RestTemplate template(){
        return new RestTemplate();
    }

    /**
     * 2020 前使用ribbon进行负载均衡
     */
    @Bean
    public IRule myRule() {
        // RandomRule 为随机策略
        return  new RandomRule();
    }
}
