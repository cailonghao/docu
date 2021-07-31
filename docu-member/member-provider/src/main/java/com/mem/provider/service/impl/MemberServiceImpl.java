package com.mem.provider.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mem.api.pojo.Member;
import com.mem.provider.dao.MemberDao;
import com.mem.provider.service.MemberService;
import com.mem.provider.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;
    @Autowired
    RedisService redisService;

    @Override
    public Member addMember(Member member) {
        Member test = selMemberByName(member.getUserName());
        if(test!=null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Member temp = memberDao.save(member);
        try {
            redisService.set(member.getUserName(), mapper.writeValueAsString(temp));
        } catch (Exception e) {
            log.info("用户添加缓存失败");
        }
        return temp;
    }

    @Override
    public Member selMember(Long id) {
        return memberDao.getOne(id);
    }

    @Override
    public Member selMemberByName(String name) {
        return memberDao.findMemberByUserName(name);
    }
}
