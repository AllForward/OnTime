package com.sln.ontime.util;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @description RSA 加解密算法
 * @author guopei
 * @date 2020-05-08 12:12
 */
public class RSAUtil {

    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 512;
    /**
     * 用于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();


    private final static String PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAl+AbNWujVwMNj/xfJyt2NCDxdAXeoVLTjMf/6otsbQ+OjBRJ7kuVfc2wncKZ5CIicFysn9bFNN8gtD+lpv5TwQIDAQABAkACkLuE7HuG1btd6kJlM/u8J65Mamj3SqHeJ+avvIkpUmpH23Fx6oykn/5utMcLqQQfuXbnvrC0lSYEfAsf6JgRAiEAzofj4NupOtJ7OaiXbyixTCq7ouqT64KPcha+AbLC4z0CIQC8QNeJ0/cYfWLe3GAuj/NQqo08TfvAMf+HYvwQp8Bq1QIgYaxelu8Lf6gfYstaotVp1A25o3iw8AZU9K3QuljiVjkCIGHeN+/AkaparUb9PMeYML+UGx7pz+KoW5bv/+lMvL0NAiEAtzIF7sfddJPCt3plj4d/OV5+hMLH2tUxr8US+Jvxa1Y=";

    private final static String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJfgGzVro1cDDY/8XycrdjQg8XQF3qFS04zH/+qLbG0PjowUSe5LlX3NsJ3CmeQiInBcrJ/WxTTfILQ/pab+U8ECAwEAAQ==";

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(PUBLIC_KEY);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(PRIVATE_KEY);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}
