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
    @Autowired
    RedisService redisService;

    @Value("${config.info}")
    private String info;

    @Resource
    AuthClient authClient;

    @RequestMapping("/getName")
    public String getName() {
        return authClient.getName() + info;
    }

    @RequestMapping("/index")
    public String index(String namespace, Principal principal) {
        log.info("principal = {}", principal);
        log.info("name = {}", principal.getName());
        log.info("namespace = {}", namespace);
        return "index" + info;
    }

    @RequestMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) throws JsonProcessingException {
        Member member = new Member();
        member.setUserName(username);
        member.setPassWord(passwordEncoder.encode(password));
        member = memberService.addMember(member);
        ObjectMapper mapper = new ObjectMapper();
        redisService.set(member.getId().toString(), mapper.writeValueAsString(member));
        return "index" + info;
    }

    @RequestMapping("/retry")
    public String retry(@RequestParam String token) throws InterruptedException {
        log.info("token = {}", token);
        throw new RuntimeException("heh");
//        Thread.sleep(5000);
//        log.info("retry token = {}", token);
//        return "retry token" + info;
    }

}
