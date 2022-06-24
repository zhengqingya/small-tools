package com.zhengqing.demo.feign;


import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.feign.rpc.IBaseClient;
import com.zhengqing.demo.feign.fallback.IDemoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * Feign接口类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
@FeignClient(value = AppConstant.RPC_DEMO,
        path = IBaseClient.API_PREFIX + "/" + AppConstant.RPC_DEMO + "/sentinel",
        // contextId: 解决同个相同client beanName冲突问题
        contextId = "IDemoClientFallback",
        fallback = IDemoClientFallback.class)
public interface IDemoClient extends IBaseClient {

    @GetMapping("/flow/common")
    Long flowCommon();

}
