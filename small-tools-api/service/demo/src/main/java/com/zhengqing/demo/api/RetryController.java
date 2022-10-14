package com.zhengqing.demo.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.demo.service.IBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 业务api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/9 1:38
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_DEMO + "/business")
@Api(tags = "retry重试")
public class RetryController extends BaseController {

    @Resource
    private IBusinessService businessService;

    @ApiOperation("服务重试")
    @GetMapping("retryable")
    @SneakyThrows(Exception.class)
    public String retryable(@RequestParam(required = false) String msg) {
        return this.businessService.retry(msg);
    }


}
