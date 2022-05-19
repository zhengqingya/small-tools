package com.zhengqing.demo.api.sentinel;

import cn.hutool.core.util.RandomUtil;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.demo.feign.IDemoClient;
import com.zhengqing.demo.service.ISentinelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p> sentinel限流测试 -- 流控 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/5/19 10:17
 */
@Slf4j
@RestController
@RequestMapping("/sentinel")
@Api(tags = "sentinel-flow")
public class SentinelFlowController extends BaseController {

    @Resource
    private ISentinelService iSentinelService;

    @Resource
    private IDemoClient iDemoClient;

    private final String FLOW_COMMON = "http://127.0.0.1:20040/sentinel/flow/common";

    /**
     * http://127.0.0.1:20040/sentinel/flow/A
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/A")
    @ApiOperation("流控-A")
    public Long flowA() {
        this.requestCommon();
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/B
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/B")
    @ApiOperation("流控-B")
    public Long flowB() {
        this.requestCommon();
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/C
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/C")
    @ApiOperation("流控-C")
    public Long flowC() {
        this.requestCommon();
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/common
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/common")
    @ApiOperation("流控-common")
    public Long common() {
        return RandomUtil.randomLong();
    }

    private Long requestCommon() {
        return this.iDemoClient.flowCommon();
    }

}
