package com.docu.auth.service;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class NoLoginService extends LoginUrlAuthenticationEntryPoint {
    public NoLoginService(String loginFormUrl) {
        super(loginFormUrl);
    }
}
