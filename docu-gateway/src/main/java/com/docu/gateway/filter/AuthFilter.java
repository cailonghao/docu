package com.docu.gateway.filter;

import com.docu.gateway.constant.GatewayConstant;
import com.docu.gateway.util.AuthUtil;
import com.docu.gateway.util.Result;
import com.docu.gateway.util.RsaUtil;
import com.docu.gateway.util.SecretUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证管理器
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getPath().pathWithinApplication().value();
        System.out.println(uri);
        if (uri.startsWith(GatewayConstant.AUTH)) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst(GatewayConstant.AUTHORIZATION);
        Jws<Claims> jws = AuthUtil.parseAuthorization(authorization);
        ServerHttpResponse httpResponse = exchange.getResponse();
        httpResponse.setStatusCode(HttpStatus.ACCEPTED);
        ObjectMapper mapper = new ObjectMapper();
        if (jws == null) {
            String data = mapper.writeValueAsString(Result.error("token过期"));
            DataBuffer buffer = httpResponse.bufferFactory().wrap(data.getBytes(StandardCharsets.UTF_8));
            return httpResponse.writeWith(Mono.just(buffer));
        }
        String user = (String) jws.getBody().get("user");
        List<String> role = (List<String>) jws.getBody().get("authorities");
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("role", role);
        String json;
        try {
            json = mapper.writeValueAsString(map);
            String sign = RsaUtil.signature(json);
            ServerHttpRequest newRequest = request.mutate()
                    .header("sign", sign)
                    .header("userInfo", json)
                    .build();
            return chain.filter(exchange.mutate().request(newRequest).build());
        } catch (Exception e) {
            log.error("包装用户json出错");
        }
        String data = mapper.writeValueAsString(Result.error("未知错误"));
        DataBuffer buffer = httpResponse.bufferFactory().wrap(data.getBytes(StandardCharsets.UTF_8));
        return httpResponse.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return GatewayConstant.AUTH_ORDER;
    }
}
