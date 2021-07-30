package com.client.mem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "docu-mem-service",fallback = MemClientImpl.class)
public interface MemClient {

    @PostMapping("/index/login")
    String login(String username);
}
