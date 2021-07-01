package com.zhengqing.demo.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 测试redis </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/29 17:46
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/redis")
@Api(tags = "测试redis")
public class RedisController extends BaseController {


    @GetMapping("set")
    @ApiOperation("设置值")
    public void set() {
        Long test = RedisUtil.incrBy("test", 1);
        log.info("incrBy: {}", test);
    }


}
