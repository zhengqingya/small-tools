package com.zhengqing.system.config;

import com.zhengqing.common.config.AppCommonRunner;
import com.zhengqing.system.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SystemRunner extends AppCommonRunner {

    @Autowired
    private ISysDictService dictService;

    @Override
    public void run(String... args) throws Exception {
        super.appRun();

        log.info("服务初始化之后，执行方法 start...");

        // 数据字典
        dictService.initCache();

        log.info("服务初始化之后，执行方法 end...");
    }

}
