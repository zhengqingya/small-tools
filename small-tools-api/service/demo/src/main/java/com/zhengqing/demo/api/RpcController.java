package com.zhengqing.demo.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.system.feign.ISystemClient;
import com.zhengqing.system.model.vo.SysDictVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 测试api
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2021/1/9 1:38
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/test")
@Api(tags = "测试api")
@AllArgsConstructor
public class RpcController extends BaseController {

    private ISystemClient systemClient;

    // http://127.0.0.1:20040/web/api/demo/test/getUpDictListFromCacheByCode
    @GetMapping("getUpDictListFromCacheByCode")
    @ApiOperation("rpc调用测试")
    public List<SysDictVO> getUpDictListFromCacheByCode() {
        return systemClient.getUpDictListFromCacheByCode("element_icon");
    }

}
