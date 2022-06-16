package com.zhengqing.mall.controller;

import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.core.constant.ServiceConstant;
import com.zhengqing.mall.service.MiniOmsOrderService;
import com.zhengqing.pay.model.bo.PayOrderNotifyBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * <p> 商城-测试 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/test")
@Api(tags = {"admin-测试api"})
public class MiniMallTestController {

    @Resource
    private MiniOmsOrderService miniOmsOrderService;

    @PostMapping("paySuccessCallback")
    @ApiOperation("支付成功回调业务处理")
    public String paySuccessCallback(@Validated @RequestBody PayOrderNotifyBO payOrderNotifyBO) {
        TenantIdContext.setTenantId(payOrderNotifyBO.getTenantId());
        this.miniOmsOrderService.paySuccessCallback(payOrderNotifyBO);
        return "OK";
    }

}
