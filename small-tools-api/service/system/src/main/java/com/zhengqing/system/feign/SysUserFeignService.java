package com.zhengqing.system.feign;

import com.zhengqing.common.base.constant.RpcConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p> 系统服务-用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:25
 */
@ApiIgnore
@Api(tags = "系统服务-用户")
@RestController
@RequestMapping(RpcConstant.RPC_API_PREFIX_SYSTEM + "/user")
public class SysUserFeignService implements ISysUserFeignApi {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public ApiResult<SysUserDetailVO> getUser(Integer userId) {
        return ApiResult.ok(this.sysUserService.detail(userId));
    }

    @Override
    public ApiResult<SysUserPermVO> getUserInfoByUsername(String username) {
        return ApiResult.ok(this.sysUserService.getUserPerm(SysUserPermDTO.builder().username(username).build()));
    }

}
