package com.mem.provider.controller;

import com.client.auth.AuthClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mem.api.pojo.Member;
import com.mem.provider.service.MemberService;
import com.mem.provider.service.RedisService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@Slf4j
@Setter
@Getter
@RestController
@RequestMapping("/index")
@RefreshScope
public class IndexController {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${config.info}")
    private String info;


    @RequestMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) throws JsonProcessingException {
        Member member = new Member();
        member.setUserName(username);
        member.setPassWord(passwordEncoder.encode(password));
        memberService.addMember(member);
        return "操作成功";
    }


    @RequestMapping("/login")
    public Member login(String username, @RequestHeader(name = "Token", required = true) String token) {
        log.info("token = {}", token);
        return memberService.selMemberByName(username);
    }


}
