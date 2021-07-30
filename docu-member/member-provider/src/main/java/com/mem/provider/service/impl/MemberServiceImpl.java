package com.mem.provider.service.impl;


import com.mem.api.pojo.Member;
import com.mem.provider.dao.MemberDao;
import com.mem.provider.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public Member addMember(Member member) {
        return memberDao.save(member);
    }

    @Override
    public Member selMember(Long id) {
        return memberDao.getOne(id);
    }
}
