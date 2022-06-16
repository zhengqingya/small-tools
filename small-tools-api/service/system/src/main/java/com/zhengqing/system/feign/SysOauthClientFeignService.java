package com.zhengqing.system.feign;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.feign.constant.RpcConstant;
import com.zhengqing.system.model.vo.SysOauthClientVO;
import com.zhengqing.system.service.ISysOauthClientService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p> oauth客户端 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:25
 */
@ApiIgnore
@Api(tags = "系统服务-oauth客户端")
@RestController
@RequestMapping(RpcConstant.RPC_API_PREFIX_SYSTEM + "/oauthClient")
public class SysOauthClientFeignService implements ISysOauthClientFeignApi {

    @Resource
    private ISysOauthClientService sysOauthClientService;

    @Override
    public ApiResult<SysOauthClientVO> getClient(String clientId) {
        return ApiResult.ok(this.sysOauthClientService.detail(clientId));
    }

}
