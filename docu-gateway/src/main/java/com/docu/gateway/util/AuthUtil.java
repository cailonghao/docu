package com.docu.gateway.util;

import com.docu.gateway.constant.GatewayConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class AuthUtil {

    public static byte[] getKey() {
        return SecretUtil.sha256(GatewayConstant.TOKEN_SECRET).getBytes(StandardCharsets.UTF_8);
    }

    public static Jws<Claims> parseAuthorization(String authorization) {
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(authorization);
        } catch (Exception e) {
            log.error("解析错误");
        }
        return jws;
    }


}
