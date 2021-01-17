package com.zhengqing.common.http;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;

public class Message {

    private Set<String> syncMsg = Collections.synchronizedSet(new LinkedHashSet<>());

    public void putMsg(String msg) {
        syncMsg.add(msg);
    }

    @Override
    public String toString() {
        if (syncMsg != null && syncMsg.size() > 0) {
            return JSON.toJSONString(syncMsg);
        }
        return null;
    }

}
