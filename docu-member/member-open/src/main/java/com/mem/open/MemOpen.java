package com.mem.open;


import com.mem.api.pojo.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "docu-mem-service",fallback = MemOpenImpl.class)
public interface MemOpen {

    @PostMapping("/index/login")
    Member login(String username, @RequestHeader(name = "token") String token);
}
