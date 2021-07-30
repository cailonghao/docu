package com.docu.gateway.fallback;

import com.docu.gateway.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DefaultFallback {

    @RequestMapping("/defaultFallback")
    public Result<String> defaultfallback() {
        return Result.error("网络超时...");
    }

}
