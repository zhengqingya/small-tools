package com.zhengqing.demo;

import com.google.common.collect.Maps;
import com.zhengqing.common.model.bo.UserTokenInfo.UserInfo;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class AppTest {

    @Test
    public void testCollection() throws Exception {
        Map<Integer, Integer> simpleMap = Maps.newHashMap();
        simpleMap.put(null, null);
        simpleMap.put(null, 1);
        simpleMap.put(1, null);
        simpleMap.put(1, 1);
        simpleMap.put(2, 1);
        System.out.println(simpleMap + "\n\n");

        Map<Object, Object> concurrentMap = Maps.newConcurrentMap();
        // concurrentMap.put(null, null);
        // concurrentMap.put(null, 1);
        // concurrentMap.put(1, null);
        concurrentMap.put(1, 1);
        concurrentMap.put(2, 1);
        System.out.println(concurrentMap + "\n\n");

        Map<Object, Object> tableMap = new Hashtable<>();
        tableMap.put(1, null);
        // tableMap.put(null, null);
        // tableMap.put(null, 1);
        tableMap.put(2, 1);
        System.out.println(tableMap + "\n\n");
    }

    @Test
    public void test01() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        Optional.ofNullable(userInfo).ifPresent(e -> {
            System.out.println(1);
            System.out.println(2);
        });
        userInfo = null;
        Optional.ofNullable(userInfo).ifPresent(userInfo1 -> userInfo1.setUserId(3));
        Optional.ofNullable(userInfo).orElseThrow(() -> new RuntimeException("xx"));
        if (1 == 1) {
            System.out.println(111);
        } else {
            System.out.println(222);
        }
    }


    @Test
    public void test02() throws Exception {

    }

}
