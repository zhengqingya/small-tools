package com.zhengqing.common.http;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import com.zhengqing.common.constant.AppConstant;

/**
 * <p>
 * 上下文基类
 * </p>
 *
 * @author : zhengqing
 * @description : 请务必在请求结束时,调用 @Method remove()
 * @date : 2020/8/1 19:07
 */
public class ContextHandler {

    public static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(1);
            map.put(AppConstant.CONTEXT_KEY_MESSAGE_LIST, new Message());
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(1);
            map.put(AppConstant.CONTEXT_KEY_MESSAGE_LIST, new Message());
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static Integer getUserId() {
        Object value = get(AppConstant.CONTEXT_KEY_USER_ID);
        return Integer.parseInt(returnObjectValue(value, String.valueOf(AppConstant.DEFAULT_CONTEXT_KEY_USER_ID)));
    }

    public static String getUsername() {
        Object value = get(AppConstant.CONTEXT_KEY_USERNAME);
        return returnObjectValue(value, AppConstant.DEFAULT_CONTEXT_KEY_USERNAME);
    }

    public static void setUserId(Integer userId) {
        set(AppConstant.CONTEXT_KEY_USER_ID, userId);
    }

    public static void setUsername(String username) {
        set(AppConstant.CONTEXT_KEY_USERNAME, username);
    }

    public static void setUserInfo(Integer userId, String username) {
        set(AppConstant.CONTEXT_KEY_USER_ID, userId);
        set(AppConstant.CONTEXT_KEY_USERNAME, username);
    }

    private static String returnObjectValue(Object value, String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }

    public static void putMessage(String msgStr) {
        Message msg = (Message)get(AppConstant.CONTEXT_KEY_MESSAGE_LIST);
        msg.putMsg(msgStr);
    }

    @SuppressWarnings("resource")
    public static void putMessage(String format, Object... args) {
        putMessage(new Formatter().format(format, args).toString());
    }

    public static Message getMessage() {
        return (Message)get(AppConstant.CONTEXT_KEY_MESSAGE_LIST);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
