package com.zhengqing.ums.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_UMS + "/user")
@Api(tags = "用户")
public class MiniUmsUserController extends BaseController {

    @Resource
    private IUmsUserService sysUserService;

    @GetMapping("getUserInfo")
    @ApiOperation("获取用户信息")
    public UmsUserVO getUserInfo(@ModelAttribute UmsUserDTO params) {
        return this.sysUserService.getUserInfo(params);
    }

}
