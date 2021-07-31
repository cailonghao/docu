package com.docu.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mem.api.pojo.Member;
import com.mem.open.MemOpen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocuUserDetailService implements UserDetailsService {

    @Autowired
    RedisService redisService;
    @Autowired
    MemOpen memOpen;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String json = redisService.get(s);
        Member member = null;
        if (json == null) {
            member = memOpen.login(s,"asd");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                member = mapper.readValue(json, Member.class);
            } catch (Exception e) {
                throw new UsernameNotFoundException("登录失败");
            }
        }
        if (member == null) {
            return null;
        }
        return User.builder().username(member.getUserName()).password(member.getPassWord()).authorities("ROLE_USER").build();
    }
}
