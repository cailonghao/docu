package com.mem.provider.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mem.provider.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("token filter...");
        String sign = request.getHeader("sign");
        String userInfo = request.getHeader("userInfo");
        log.info("sign = {}", sign);
        log.info("userInfo = {}", userInfo);
        if (RsaUtil.verify(userInfo, sign)) {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(userInfo, Map.class);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            List<String> list = (List<String>) map.get("role");
            Authentication authentication =
                    new TestingAuthenticationToken(map.get("user"), "", list.toArray(new String[list.size()]));
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        }
    }
}
