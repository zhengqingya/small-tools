package com.zhengqing.system.feign.fallback;

import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.system.feign.ISysOauthClientFeignApi;
import com.zhengqing.system.model.vo.SysOauthClientVO;
import org.springframework.stereotype.Component;

/**
 * <p>
 * oauth客户端
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
@Component
public class ISysOauthClientFeignFallback implements ISysOauthClientFeignApi {

    @Override
    public ApiResult<SysOauthClientVO> getClient(String clientId) {
        return ApiResult.busy();
    }

}
