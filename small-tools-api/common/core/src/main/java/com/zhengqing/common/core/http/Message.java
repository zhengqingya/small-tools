package com.zhengqing.common.core.http;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Message {

    private Set<String> syncMsg = Collections.synchronizedSet(new LinkedHashSet<>());

    public void putMsg(String msg) {
        this.syncMsg.add(msg);
    }

    @Override
    public String toString() {
        if (this.syncMsg != null && this.syncMsg.size() > 0) {
            return JSON.toJSONString(this.syncMsg);
        }
        return null;
    }

}
