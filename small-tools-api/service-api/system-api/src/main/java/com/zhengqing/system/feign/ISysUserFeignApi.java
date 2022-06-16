package com.zhengqing.system.feign;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.feign.constant.RpcConstant;
import com.zhengqing.system.feign.fallback.ISysUserFeignFallback;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
@FeignClient(value = RpcConstant.RPC_SYSTEM,
        path = RpcConstant.RPC_API_PREFIX_SYSTEM + "/user",
        contextId = "ISysUserFeignApi",
        fallback = ISysUserFeignFallback.class)
public interface ISysUserFeignApi {

    /**
     * 用户信息
     *
     * @param userId 用户id
     * @return 用户详情
     * @author zhengqingya
     * @date 2022/6/1 15:42
     */
    @GetMapping("getUser/{userId}")
    ApiResult<SysUserDetailVO> getUser(@PathVariable Integer userId);

    /**
     * 用户信息
     *
     * @param username 用户名
     * @return 用户详情
     * @author zhengqingya
     * @date 2022/6/1 15:42
     */
    @GetMapping("getUserInfoByUsername")
    ApiResult<SysUserPermVO> getUserInfoByUsername(@RequestParam String username);

}
