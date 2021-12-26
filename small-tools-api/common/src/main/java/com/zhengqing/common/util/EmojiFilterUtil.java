package com.zhengqing.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> 过滤emoji表情符号工具类 </p>
 *
 * @author zhengqingya
 * @description 主要解决 => UTF-8编码有可能是两个、三个、四个字节。Emoji表情是4个字节，而Mysql的utf8编码最多3个字节，所以数据插不进去。
 * @date 2021/12/26 2:26 下午
 */
public class EmojiFilterUtil {

    /**
     * 是否含有emoji表情符号
     *
     * @param codePoint 字符
     * @return true->是 false->否
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param sourceStr 源文字
     * @return 过滤后的字符
     */
    public static String filterEmoji(String sourceStr) {
        if (StringUtils.isBlank(sourceStr)) {
            return sourceStr;
        }
        StringBuilder buf = null;
        int len = sourceStr.length();
        for (int i = 0; i < len; i++) {
            char codePoint = sourceStr.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(sourceStr.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return sourceStr;
        } else {
            if (buf.length() == len) {
                buf = null;
                return sourceStr;
            } else {
                return buf.toString();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("111 \uD83E\uDDF8");
        System.out.println(EmojiFilterUtil.filterEmoji("111 \uD83E\uDDF8"));
        System.out.println(EmojiFilterUtil.filterEmoji("111 \ud83d\udfff"));
    }

}
