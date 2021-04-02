package com.zhengqing.demo;

import java.util.Hashtable;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

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

}
