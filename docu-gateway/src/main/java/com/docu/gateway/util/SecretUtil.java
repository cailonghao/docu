package com.docu.gateway.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class SecretUtil {


    public static String sha256(String str) {
        return DigestUtils.sha256Hex(str);
    }


    public static String base64Encode(String str) {
        return Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
    }
}
