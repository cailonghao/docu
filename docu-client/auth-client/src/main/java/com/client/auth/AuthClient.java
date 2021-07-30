package com.client.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "docu.auth.dev.service",fallback = AuthClientImpl.class)
public interface AuthClient {

    @PostMapping("/index/getName")
    String getName();

}
