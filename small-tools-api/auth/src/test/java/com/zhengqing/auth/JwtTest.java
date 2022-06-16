package com.zhengqing.auth;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

/**
 * <p> Jwt测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/13 14:43
 */
public class JwtTest {

    @Test
    public void testCreateJwt() throws Exception {
        // 1、创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                // 秘钥位置
                new ClassPathResource("jwt.jks"),
                // 秘钥库密码
                "123456".toCharArray()
        );
        // 2、基于工厂拿到私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("small-tools", "123456".toCharArray());

        // 转化为rsa私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 3、生成jwt
        Map<String, String> map = Maps.newHashMap();
        map.put("username", "zq");
        map.put("password", "123456");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String jwtEncoded = jwt.getEncoded();
        System.out.println("jwtEncoded:" + jwtEncoded);
        String claims = jwt.getClaims();
        System.out.println("claims:" + claims);
    }

    @Test
    public void testParseJwt() throws Exception {
        // 基于公钥去解析jwt
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoienEifQ.KoD8HDRiaKzGQwC493tTrIO2YbCNdkMR-ENPHab31mhFYE1K2IR3knxQ-yaVEg1-lJPbVSmZV4mrsQGKq17VetZhl9onvKAIsupwbi0CI0riPknQsTf6UEMLwgTY7cLbf6cLUa5XaoqiH0wVTM-xCLnBVIXEpjM8jMZQ_5V47TGsa5rAIYePK_FUpxhhuVKcbIed-DvkGBvNwOuD7iVh__2iLSSGh2V-4LkJeJXDGloReb4srS1sdbDK2gdmUD3VljXURU2HAIG0QvZA7PCe1BWgAWBlFaa2VO1NxysWY5SvMgR7vVjgnwH6-iBkbpzwmZMdXU_Vc-mDZaxvv575YQ";
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoDx/QIbsN80KBXZ+yxT+\n" +
                "UcwTvqF1T92w9ZkwDdzNLQLX0XV7Th5sFRAWRgR2cCBPas7V3ZWFNvAWiUUjTrYK\n" +
                "mY7wUzTHyYR9nekoaw6VceNvw109ycNjZhJGLoYkz/MMSvBfZsdtxjeWZ2xhuGw1\n" +
                "Ufhz8HbQFU0UQO8Zb+OdRq36FzN5+MHX/rgMyZSBrEf2cJnoeVv0Jb4RQMtpOs4T\n" +
                "g18/i5iNJICecrmWiady6pUTgidXqHHCLL76GIPPSmWir84AR0PlZqSRPj51DXnI\n" +
                "zOXkbptxqhBmsIquP/FGTQemnySKxHBe9q36gcfCdPHQqX64k2D15i0HVlYyznOA\n" +
                "0QIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        // 解析令牌
        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        // 获取负载
        String claims = token.getClaims();
        System.out.println(claims);
    }

}
