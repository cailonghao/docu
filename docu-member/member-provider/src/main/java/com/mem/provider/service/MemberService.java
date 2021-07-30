package com.mem.provider.service;


import com.mem.api.pojo.Member;

public interface MemberService {

    Member addMember(Member member);

    Member selMember(Long id);
}
