package com.mem.provider.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class RsaUtil {

    public static final String RSA_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    private static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4ehUCMxlCLfaX7pTxPF7ZvpQf\n" +
            "83wW8mW1ckvrNCRZSEZLijn1Az4xvBKtBDDhOf8yoCyNaUOoDLNEZpv2dRftCnYV\n" +
            "3uTvL3ftoQeinMIs7YsB7EiRsXmknQ43bWWi1CU8HJ9kI+4sYKL88zPUbS7GB3Ou\n" +
            "Uom0FE5+SMdzPGTyFwIDAQAB";
    private static final String DEFAULT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALh6FQIzGUIt9pfu\n" +
            "lPE8Xtm+lB/zfBbyZbVyS+s0JFlIRkuKOfUDPjG8Eq0EMOE5/zKgLI1pQ6gMs0Rm\n" +
            "m/Z1F+0KdhXe5O8vd+2hB6KcwiztiwHsSJGxeaSdDjdtZaLUJTwcn2Qj7ixgovzz\n" +
            "M9RtLsYHc65SibQUTn5Ix3M8ZPIXAgMBAAECgYEApIOS6rRBKUiuKc4Fm6C4Ab04\n" +
            "gcn09s3SsF6un6GPbOPMdJ9fkkYDF/1eNlePc7vx/ubi9HVUdbBlynErUz4XR7Id\n" +
            "RGNOI8Jblhr0yyjVL1gj/nCRCiv/29GDYBNpbFi7HHni8nS6xsmhh78j0RhRT1vE\n" +
            "+FEfhC48JS/uqpCKaoECQQD0Ern/jarbJ0u3S2FHz7Fwwm/cddPoa4Fw/XHTgo+A\n" +
            "TwpGbnrsS7Qmw2QL/s7eSaPen+sesFvdtOLS1+fALE7XAkEAwX3Tfijl4bA4IQ/b\n" +
            "/BW4Xt/D9ntSnptQ5BFKYO8DN2ug0mVd2rZct3pvalxOe7re1M807MbeHL8qX2i4\n" +
            "dchOwQJAb2QUizNGpNpcTf0TGskJPGMg1kbYPZzC6KRizJsrju2DzP0jELDEOWS3\n" +
            "Rlo2Y3DMXxAxKEJoytiBJKTw2d3e5QJAU/7tat0SPlEjbKbX1q0PJpB5H20Vxqii\n" +
            "Cv++jCn1+X8Nh4hdn+5+fV/3MfL0Vx4eUb/Jt+f00AHkwRpSyT61gQJAR5tZZAkB\n" +
            "9wjNXgChqqJd0195mW7U1QRbLCFdUi+HMIxXsc4XpmXCV1tUQyvkytiwBEkg7kgL\n" +
            "jkgddbqYT+es+A==";


    /**
     * 获取公钥 字符串
     *
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            log.error("公钥读取失败 {}", e.getMessage());
        }
        return null;

    }

    /**
     * 获取私钥
     */
    public static PrivateKey getPrivateKey(String privetKey) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec =
                    new PKCS8EncodedKeySpec(Base64.decodeBase64(privetKey));
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            log.error("私钥读取失败 {}", e.getMessage());
        }
        return null;
    }

    /**
     * 公钥加密
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(DEFAULT_PUBLIC_KEY));
            String outStr = Base64.encodeBase64String(
                    cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
            return outStr;
        } catch (Exception e) {
            log.error("加密失败 {}", e.getMessage());
        }
        return null;
    }

    /**
     * 私钥解密
     */
    public static String decrypt(String data) {
        try {
            byte[] inputByte = Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(DEFAULT_PRIVATE_KEY));
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            log.error("解密失败 {}", e.getMessage());
        }
        return null;
    }

    /**
     * 私钥签名
     */
    public static String signature(String content) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(getPrivateKey(DEFAULT_PRIVATE_KEY));
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            log.error("签名失败 -> {}", e.getMessage());
        }
        return null;
    }

    /**
     * 公钥验签
     */
    public static boolean verify(String content, String sign) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(getPublicKey(DEFAULT_PUBLIC_KEY));
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.decodeBase64(sign.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("验签失败 {}", e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        String d1 = "cainiao1234";
        String d2 = encrypt(d1);
        String d3 = decrypt(d2);
        log.info("d3 = {}", d3);
        log.info("----------------签名------------");
        String sign = signature(d1);
        log.info("sign = {}", sign);
        boolean ok = verify(d1, sign);
        log.info("ok = {}", ok);

    }
}
