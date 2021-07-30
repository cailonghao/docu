package com.docu.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DocuUserDetailService implements UserDetailsService {

    @Autowired
    RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String json = redisService.get(s);
        return User.builder().username("rookie").password(new BCryptPasswordEncoder().encode("123456")).authorities("ROLE_USER").build();
    }
}
