package com.zhengqing.demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.demo.entity.Demo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
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
        Demo demo = new Demo();
        demo.setId(1L);
        Optional.ofNullable(demo).ifPresent(e -> {
            System.out.println(1);
            System.out.println(2);
        });
        demo = null;
        Optional.ofNullable(demo).ifPresent(userInfo1 -> userInfo1.setId(3L));
        Optional.ofNullable(demo).orElseThrow(() -> new RuntimeException("xx"));
        if (1 == 1) {
            System.out.println(111);
        } else {
            System.out.println(222);
        }
    }

    @Test
    public void test02() throws Exception {
        // 尾部插入： 10w内LinkedList快    100w上ArrayList快
        // 头部插入： LinkedList都快
        int testSum = 1000000;
        LocalDateTime saveBeforeDateTime = LocalDateTime.now();
        List<Demo> demoList = Lists.newArrayList();
        for (int i = 0; i < testSum; i++) {
            demoList.add(Demo.builder().username("test - " + i).password("123456").build());
        }
        LocalDateTime saveAfterDateTime = LocalDateTime.now();
        Duration duration = Duration.between(saveBeforeDateTime, saveAfterDateTime);
        log.info("ArrayList 用时 :【{} s】", duration.toMillis());

        // ----------------------------------------------------------

        LocalDateTime saveBeforeDateTime2 = LocalDateTime.now();
        List<Demo> demoList2 = Lists.newLinkedList();
        IntStream.range(0, testSum).forEach(i -> {
            demoList2.add(0, Demo.builder().username("test - " + i).password("123456").build());
        });
//        for (int i = 0; i < testSum; i++) {
//            demoList2.add(0, Demo.builder().username("test - " + i).password("123456").build());
//        }
        LocalDateTime saveAfterDateTime2 = LocalDateTime.now();
        Duration duration2 = Duration.between(saveBeforeDateTime2, saveAfterDateTime2);
        log.info("LinkedList 用时 :【{} s】", duration2.toMillis());
    }

    @Test
    public void test03() throws Exception {
        int sum = 10000000;
        this.headInsert(Lists.newArrayList(), sum);
        this.headInsert2(Lists.newLinkedList(), sum);

    }

    private void headInsert(ArrayList arrayList, int size) {
        long start = System.currentTimeMillis();
        IntStream.range(0, size).forEach(i -> {
            arrayList.add(i);
        });
        long end = System.currentTimeMillis();
        System.out.println("ArrayList headInsert cost:" + (end - start) + "ms");
    }

    private void headInsert2(LinkedList linkedList, int size) {
        long start = System.currentTimeMillis();
        IntStream.range(0, size).forEach(i -> {
            linkedList.add(0, i);
        });
        long end = System.currentTimeMillis();
        System.out.println("LinkedList headInsert cost:" + (end - start) + "ms");
    }

    @Test
    public void test04() throws Exception {
        LocalDateTime handleBeforeTime = LocalDateTime.now();
        log.debug("处理开始时间：{}", handleBeforeTime);
        TimeUnit.SECONDS.sleep(2);
        LocalDateTime handleAfterTime = LocalDateTime.now();
        log.debug("处理完成时间：{}", handleAfterTime);
        log.debug("处理时间：{} s", Duration.between(handleBeforeTime, handleAfterTime).toMillis());

//        log.error("handleBeforeTime:{} handleAfterTime:{}", handleBeforeTime, handleAfterTime);
    }

    @Test
    public void test05() throws Exception {
        List<Integer> list = Lists.newArrayList();
        Optional.ofNullable(list).ifPresent(e -> log.info(e.toString()));
    }

    @Test
    public void test06() throws Exception {

    }


}
