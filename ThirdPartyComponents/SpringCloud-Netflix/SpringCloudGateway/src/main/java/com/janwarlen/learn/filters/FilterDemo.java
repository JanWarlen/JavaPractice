package com.janwarlen.learn.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FilterDemo implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //先获取ServerHttpRequest对象，注意不是HttpServletRequest
        ServerHttpRequest request = exchange.getRequest();
        //打印一下所有的请求参数
        System.out.println(request.getQueryParams());
        //判断是否包含test参数，且参数值为1
        List<String> value = request.getQueryParams().get("filter");
        if(value != null && value.contains("1")) {
            // 继续执行
            return chain.filter(exchange);
        }else {
            // 直接返回，不继续执行
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        // 过滤器的顺序，0 表示第一个
        return 0;
    }
}
