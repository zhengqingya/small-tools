package com.zhengqing.system.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.system.model.dto.SysOauthRemoveBindDTO;
import com.zhengqing.system.model.dto.SysOauthSaveDTO;
import com.zhengqing.system.model.vo.SysOauthDataListVO;
import com.zhengqing.system.service.ISysOauthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;

/**
 * <p>
 * 系统管理 - 用户三方授权 Controller
 * </p>
 *
 * @author: zhengqing
 * @description : 可参考： https://justauth.wiki
 * @date : 2020/6/21 21:18
 */
@Slf4j
@RestController
@RequestMapping("/web/api/oauth")
@Api(tags = "系统管理 - 三方登录接口")
public class SysOauthController extends BaseController {

    @Autowired
    private ISysOauthService sysOauthService;

    @RequestMapping("{oauthType}")
    @ApiOperation("第三方账号授权")
    public String handleOauth(@PathVariable String oauthType, HttpServletResponse response) {
        return sysOauthService.handleOauth(oauthType, response);
    }

    @RequestMapping("{oauthType}/callback")
    @ApiOperation("第三方账号授权回调处理")
    public void handleCallback(@PathVariable String oauthType, AuthCallback callback, HttpServletResponse response) {
        sysOauthService.handleCallback(oauthType, callback, response);
    }

    @PostMapping("bindThirdPart")
    @ApiOperation("第三方账号绑定")
    public Integer handleBindThirdPartData(@Validated @RequestBody SysOauthSaveDTO params) {
        return sysOauthService.addOrUpdateData(params);
    }

    @GetMapping("getOauthDataList")
    @ApiOperation("获取第三方账号绑定授权数据")
    public List<SysOauthDataListVO> getOauthDataList(@RequestParam Integer userId) {
        return sysOauthService.getOauthDataList(userId);
    }

    @PostMapping("removeBind")
    @ApiOperation("解除第三方账号绑定")
    public void removeBind(@RequestBody SysOauthRemoveBindDTO params) {
        sysOauthService.removeBind(params);
    }

}
