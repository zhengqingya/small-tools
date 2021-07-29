package com.zhengqing.demo.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.aspect.config.BeanSelfAware;
import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.util.RestTemplateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        b(new BaseDTO());
        self.b(new BaseDTO());
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

}
