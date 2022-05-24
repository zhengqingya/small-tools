package com.zhengqing.demo.api.sentinel;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.demo.feign.IDemoClient;
import com.zhengqing.demo.service.ISentinelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p> sentinel限流测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/5/19 10:17
 */
@Slf4j
@RestController
@RequestMapping("/sentinel")
@Api(tags = "sentinel")
public class SentinelController extends BaseController {

    @Resource
    private ISentinelService iSentinelService;

    @Resource
    private IDemoClient iDemoClient;

    private Long num = 0L;

    private Long requestCommon() {
        return this.iDemoClient.flowCommon();
    }

    public Long common() {
        this.iSentinelService.common();
        return 1L;
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/A
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/A")
    @ApiOperation("A")
    public Long flowA() {
        this.num++;
        if (this.num > 6) {
            return this.num;
        }
        // 只有正常请求common，才能启动链路限流规则
//        this.requestCommon();
        this.common();
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/B
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/B")
    @ApiOperation("B")
    public Long flowB() {
//        this.requestCommon();
        this.common();
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/C
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/C")
    @ApiOperation("C")
    public Long flowC() {
        TimeUnit.MILLISECONDS.sleep(200);
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/D
     */
    @SneakyThrows(Exception.class)
    @GetMapping("flow/D")
    @ApiOperation("D")
    public Long flowD() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            if (!BlockException.isBlockException(e)) {
                Tracer.trace(e);
            }
            throw e;
        }
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/flow/E?paramA=1&paramB=2
     */
    @SentinelResource("/sentinel/flow/EE")
    @SneakyThrows(Exception.class)
    @GetMapping("flow/E")
    @ApiOperation("E")
    public Long flowE(@RequestParam(required = false) String paramA,
                      @RequestParam(required = false) String paramB) {
        // paramA in index 0, paramB in index 1.
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/F
     */
    @SneakyThrows(Exception.class)
    @GetMapping("F")
    @ApiOperation("F")
    public Long F() {
        return RandomUtil.randomLong();
    }

    /**
     * http://127.0.0.1:20040/sentinel/G
     */
    @GetMapping("G")
    @ApiOperation("G")
    @SneakyThrows(Exception.class)
    @SentinelResource(value = "/sentinel/G", blockHandler = "blockHandlerForG")
    public Long G() {
        return RandomUtil.randomLong();
    }


    public Long blockHandlerForG(BlockException ex) {
        return 666L;
    }

}

