package com.zhengqing.system.feign;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.feign.constant.RpcConstant;
import com.zhengqing.system.feign.fallback.ISysOauthClientFeignFallback;
import com.zhengqing.system.model.vo.SysOauthClientVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * oauth客户端
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
@FeignClient(value = RpcConstant.RPC_SYSTEM,
        path = RpcConstant.RPC_API_PREFIX_SYSTEM + "/oauthClient",
        contextId = "ISysOauthClientFeignApi",
        fallback = ISysOauthClientFeignFallback.class)
public interface ISysOauthClientFeignApi {

    /**
     * 详情
     *
     * @param clientId 客户端ID
     * @return 详情
     * @author zhengqingya
     * @date 2022/06/10 16:25
     */
    @GetMapping("getClient/{clientId}")
    ApiResult<SysOauthClientVO> getClient(@PathVariable String clientId);

}
