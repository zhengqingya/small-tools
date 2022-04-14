package com.zhengqing.demo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p> HeapTest </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/4/14 15:04
 */
public class HeapTest {
    static class OOMObject {
        // 一个对象为64K
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void main(String[] args) {
        List<OOMObject> list = Lists.newArrayList();
        // 添加1000次
        for (int i = 0; i < 1000; i++) {
            //稍作延迟，使得监控曲线容易观察
            try {
                Thread.sleep(50);
            } catch (Exception e) {

            }
            list.add(new OOMObject());
        }
        System.gc();
    }
}
