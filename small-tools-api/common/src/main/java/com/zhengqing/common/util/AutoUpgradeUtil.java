package com.zhengqing.common.util;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自增编号工具类$
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/10/13$ 14:08$
 */
@Slf4j
public class AutoUpgradeUtil {

    /**
     * 自增编号 ex:0001 -> 0002
     *
     * @param code:
     *            编号
     * @return: 自增后的编号
     * @author : zhengqing
     * @date : 2020/10/13 14:13
     */
    public static String autoUpgrade(String code) {
        String codeNew = "0001";
        if (StringUtils.isNotBlank(code)) {
            code = code.trim();
            codeNew = String.format("%0" + code.length() + "d", Integer.parseInt(code) + 1);
        }
        log.debug("《自增编号》 旧数据：【{}】  新数据：【{}】", code, codeNew);
        return codeNew;
    }

    public static void main(String[] args) {
        autoUpgrade("9999");
    }

}
