package com.zhengqing.demo.api;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.aspect.config.BeanSelfAware;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.custom.limit.ApiLimit;
import com.zhengqing.common.http.ApiResult;
import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.util.EmailUtil;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.common.util.RestTemplateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RateIntervalUnit;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 测试api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/9 1:38
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/test")
@Api(tags = "测试api")
public class TestController extends BaseController implements BeanSelfAware {

    /**
     * 注入自己的AOP代理对象
     */
    private TestController self;

    // http://127.0.0.1:20040/web/api/demo/test
    @GetMapping("")
    @ApiOperation("a")
    public void a(@ModelAttribute BaseDTO filter) {
        log.debug("AAA: {}", filter.getCurrentUserId());
        log.debug("AAA: {}", filter.getCurrentUsername());
        this.b(new BaseDTO());
        this.b(new BaseDTO());
        this.self.b(new BaseDTO());
    }

    public void b(BaseDTO filter) {
        log.debug("BBB: {}", filter.getCurrentUserId());
        log.debug("BBB: {}", filter.getCurrentUsername());
    }

    @Override
    public void setSelf(Object proxyBean) {
        this.self = (TestController) proxyBean;
        // 如果输出true标识AOP代理对象注入成功
        log.debug("TestController: 【{}】", AopUtils.isAopProxy(this.self));
    }

    @GetMapping("restTemplate")
    @ApiOperation("restTemplate")
    public String restTemplate() {
        return RestTemplateUtil.get("http://127.0.0.1:20040/web/api/demo/test", String.class).getBody();
    }

    @PostMapping("sendMail")
    @ApiOperation("sendMail")
    public String sendMail(@RequestParam String sendTo) {
        EmailUtil.sendMail("test", "content", sendTo);
        return "SUCCESS";
    }

    @ApiOperation("testHandleReturnValue")
    @GetMapping("testHandleReturnValue")
    public ApiResult<List<String>> testHandleReturnValue() {
        return ApiResult.ok("SUCCESS", Lists.newArrayList("hello world"));
    }


    //    @ApiLimit(key = "API_LIMIT")
//    @ApiLimit(key = "'API_LIMIT' + ':' + #key")
    @ApiLimit(key = "'" + AppConstant.API_LIMIT_KEY + "' + ':' + #key",
            rateInterval = 3,
            rateIntervalUnit = RateIntervalUnit.SECONDS,
            msg = "3秒内不能重复此操作！")
    @ApiOperation("测试限流")
    @GetMapping("apiLimit")
    public String apiLimit(@RequestParam String key) {
        return "OK";
    }

    @ApiOperation("并发测试")
    @GetMapping("contiperf")
    @SneakyThrows(Exception.class)
    public String contiPerf(@RequestParam String key) {
//        TimeUnit.SECONDS.sleep(1);
        Long num = RedisUtil.incrBy(key, 1);
        log.info("num: {}", num);
        return "OK: " + RandomUtil.randomNumber();
    }

}
