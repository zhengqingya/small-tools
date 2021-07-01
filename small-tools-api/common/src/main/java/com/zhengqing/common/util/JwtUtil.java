package com.zhengqing.common.util;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.exception.ParameterException;
import com.zhengqing.common.model.bo.UserTokenInfo;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * <p>
 * jwt工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 21:52
 */
@Slf4j
public class JwtUtil {

    /**
     * 创建秘钥
     */
    private static final byte[] SECRET = "www.zhengqingya.com.java.20200801".getBytes();

    /**
     * 生成 jwt
     *
     * @param userInfo
     * @return
     * @throws MyException
     */
    public static String buildJWT(UserTokenInfo userInfo) {
        try {
            Map<String, String> map = BeanUtils.describe(userInfo);
            // 创建JWS头，设置签名算法和类型
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
            // 将负载信息封装到Payload中
            Payload payload = new Payload(new JSONObject(map));
            // 创建JWS对象
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            // 创建HMAC签名器
            JWSSigner jwsSigner = new MACSigner(SECRET);
            // 签名
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new MyException("JWT生成失败!");
        }
    }

    /**
     * 解析并验证jwt
     *
     * @param token
     * @return
     * @throws MyException
     */
    public static UserTokenInfo checkJWT(String token) {
        try {
            // 从token中解析JWS对象
            JWSObject jwsObject = JWSObject.parse(token);
            // 创建HMAC验证器
            JWSVerifier jwsVerifier = new MACVerifier(SECRET);
            if (!jwsObject.verify(jwsVerifier)) {
                throw new ParameterException("token签名不合法!");
            }
            JSONObject map = verify(jwsObject, jwsVerifier);
            UserTokenInfo userInfo = JSON.parseObject(map.toJSONString(), UserTokenInfo.class);
            if (userInfo.getUserId() <= 0) {
                throw new ParameterException("token失效");
            }
            if (userInfo.getExpirationTime() < System.currentTimeMillis()) {
                throw new ParameterException("token已过期");
            }
            return userInfo;
        } catch (Exception e) {
            throw new ParameterException("token不合法");
        }
    }

    /**
     * 验证token信息
     *
     * @param jwsObject
     * @param jwsVerifier
     * @return
     * @throws JOSEException
     */
    private static JSONObject verify(JWSObject jwsObject, JWSVerifier jwsVerifier) throws JOSEException {
        // 获取到载荷
        Payload payload = jwsObject.getPayload();
        // 判断token
        if (jwsObject.verify(jwsVerifier)) {
            return payload.toJSONObject();
        }
        throw new ParameterException("token不合法");
    }

    public static void main(String[] args) {
        UserTokenInfo userTokenInfo = new UserTokenInfo(1, "zhengqingya", "zhengqingya",
                System.currentTimeMillis() + AppConstant.DEFAULT_EXPIRES_TIME);
        try {
            String jwt = buildJWT(userTokenInfo);
            log.debug("生成的jwt：【{}】", jwt);
            UserTokenInfo userTokenInfoNew = checkJWT(jwt);
            log.debug("用户信息：【{}】", userTokenInfoNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
