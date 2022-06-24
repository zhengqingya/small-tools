package com.zhengqing.system.feign.fallback;

import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.system.feign.ISysUserFeignApi;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import org.springframework.stereotype.Component;

/**
 * <p> 用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:23
 */
@Component
public class ISysUserFeignFallback implements ISysUserFeignApi {

    @Override
    public ApiResult<SysUserDetailVO> getUser(Integer userId) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<SysUserPermVO> getUserInfoByUsername(String username) {
        return ApiResult.busy();
    }

}
