package com.zhengqing.common.api;

import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.http.ContextHandler;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Controller基类
 * </p>
 *
 * @description:
 * @author: zhengqing
 * @date: 2019/8/17 0017 19:53
 */
@Slf4j
@RestController
@Api(tags = "接口")
public class BaseController {

    /**
     * 获取当前登录人ID
     *
     * @return: 当前登录人ID
     * @author : zhengqing
     * @date : 2020/8/30 15:41
     */
    protected Integer appGetCurrentUserId() {
        return ContextHandler.getUserId();
    }

}
