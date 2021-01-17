package com.zhengqing.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.Base64Utils;

import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.MyException;

/**
 * <p>
 * DES 加密/解密工具类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/12 0:40
 */
public class DESUtil {

    private static final String DES_ALGORITHM = "DES";

    public static void main(String[] args) {
        System.out.println(decryption("lMQH4tCcD1OiP7uElwWJY8Y3F7W3QshS", "bzszwrtqyjyjpt"));
        System.out.println(decryption("49e61954990acbe3bdbdbe8f865f064a8ed4d77c", AppConstant.DES_KEY));
    }

    /**
     * DES解密
     *
     * @param secretData
     *            密码字符串
     * @param secretKey
     *            解密密钥
     * @return 原始字符串
     */
    public static String decryption(String secretData, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));
            byte[] buf = cipher.doFinal(Base64Utils.decodeFromString(secretData));
            return new String(buf);
        } catch (Exception e) {
            throw new MyException("DES解密失败：" + e.getMessage());
        }
    }

    /**
     * 获取秘密密钥
     *
     * @param secretKey:
     * @return: javax.crypto.SecretKey
     */
    private static SecretKey generateKey(String secretKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes("utf-8"));
            keyFactory.generateSecret(keySpec);
            return keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            throw new MyException("DES-获取秘密密钥失败：" + e.getMessage());
        }
    }

}
