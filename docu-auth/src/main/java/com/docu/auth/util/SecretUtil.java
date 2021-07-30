package com.docu.auth.util;

import org.apache.commons.codec.digest.DigestUtils;

public class SecretUtil {


    public static String sha256(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
