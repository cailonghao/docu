package com.docu.gateway.filter;

import com.docu.gateway.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求的计时
 * https://juejin.cn/post/6844903846469189645#heading-8
 */
@Component
@Slf4j
public class TimingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String uri = exchange.getRequest().getPath().pathWithinApplication().value();
        return chain.filter(exchange).doFinally(f -> {
            log.info("{} 处理时长 {}ms", uri, System.currentTimeMillis() - startTime);
        });
    }

    @Override
    public int getOrder() {
        return GatewayConstant.TIMING_ORDER;
    }
}
