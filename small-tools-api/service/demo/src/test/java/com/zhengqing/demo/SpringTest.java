package com.zhengqing.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p> Spring测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/6 10:47
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SpringTest {

    @Test
    public void test() throws Exception {
        log.info("xxx");
    }

}
