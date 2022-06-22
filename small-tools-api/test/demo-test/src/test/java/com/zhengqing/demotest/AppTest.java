package com.zhengqing.demotest;

import org.junit.Test;

public class AppTest {

    @Test
    public void test01() throws Exception {
        String url = "https://www.zhengqingya.com/test.png";
        String[] urlArray = url.split("/");
        System.out.println(urlArray[urlArray.length - 1]);
    }

}
