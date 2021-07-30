package com.mem.provider.dao;


import com.mem.api.pojo.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, Long> {


    Member findMemberByUserName(String userName);

}
