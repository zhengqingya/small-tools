package com.zhengqing.common.im.config;

import com.zhengqing.common.im.util.ImUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 服务初始化之后，执行方法
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/5/22 19:29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImClientRunner implements CommandLineRunner {

    private final ImUtil imUtil;

    @Override
    public void run(String... args) throws Exception {
        this.imUtil.connect();
    }
}
