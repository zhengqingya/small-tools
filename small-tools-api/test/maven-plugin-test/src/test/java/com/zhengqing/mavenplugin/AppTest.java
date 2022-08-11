package com.zhengqing.mavenplugin;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class AppTest {

    @Test
    @SneakyThrows
    public void test() {
        MyPlugin myPlugin = new MyPlugin();
        myPlugin.execute();
    }

}
