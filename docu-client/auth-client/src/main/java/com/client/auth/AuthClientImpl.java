package com.client.auth;

import org.springframework.stereotype.Component;

@Component
public class AuthClientImpl implements AuthClient{
    @Override
    public String getName() {
        return "服务暂时不可用!";
    }
}
