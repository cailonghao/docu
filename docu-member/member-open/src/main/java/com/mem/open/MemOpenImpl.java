package com.mem.open;

import com.mem.api.pojo.Member;
import org.springframework.web.bind.annotation.RequestHeader;

public class MemOpenImpl implements MemOpen {

    @Override
    public Member login(String username, @RequestHeader(name = "Token", required = true) String token) {
        return null;
    }
}
