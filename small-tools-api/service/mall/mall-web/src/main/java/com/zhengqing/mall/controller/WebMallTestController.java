package com.zhengqing.mall.controller;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.core.constant.ServiceConstant;
import com.zhengqing.mall.service.WebOmsOrderAfterSaleService;
import com.zhengqing.pay.model.bo.PayOrderNotifyBO;
import com.zhengqing.system.enums.SysPropertyKeyEnum;
import com.zhengqing.system.feign.ISysPropertyFeignApi;
import com.zhengqing.system.model.vo.SysPropertyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p> 测试 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/test")
@Api(tags = {"test-api"})
public class WebMallTestController {

    @Resource
    private ISysPropertyFeignApi ISysPropertyFeignApi;

    @Resource
    private WebOmsOrderAfterSaleService webOmsOrderAfterSaleService;

    @GetMapping("getPropertyByKey")
    @ApiOperation("获取系统属性")
    public ApiResult<SysPropertyVO> getPropertyByKey() {
        return this.ISysPropertyFeignApi.getByKey(SysPropertyKeyEnum.MALL_ORDER_SET_STOCK_CHECK_TYPE.getKey());
    }

    @PostMapping("refundSuccessCallback")
    @ApiOperation("退款成功回调")
    public String refundSuccessCallback(@RequestBody PayOrderNotifyBO payOrderNotifyBO) {
        this.webOmsOrderAfterSaleService.refundSuccessCallback(payOrderNotifyBO);
        return "OK";
    }

}
