package com.zhengqing.demo.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.util.RequestContextUtil;
import com.zhengqing.demo.threadpool.SystemTaskThread;
import com.zhengqing.system.feign.ISystemClient;
import com.zhengqing.system.model.vo.SysDictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/9 1:38
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/test")
@Api(tags = "测试api")
@AllArgsConstructor
public class RpcController extends BaseController {

    private ISystemClient systemClient;

    private SystemTaskThread systemTaskThread;

    // http://127.0.0.1:20040/web/api/demo/test/getUpDictListFromCacheByCode
    @GetMapping("getUpDictListFromCacheByCode")
    @ApiOperation("rpc调用测试")
    public List<SysDictVO> getUpDictListFromCacheByCode() {
        return this.systemClient.getUpDictListFromCacheByCode("element_icon");
    }

    @GetMapping("getContextUserId")
    @ApiOperation("rpc调用测试 - Async")
    public void getContextUserId() {
        Map<String, String> headerMap = RequestContextUtil.getHeaderMap();
        log.info("主线程请求头值: {}", headerMap.get("userId"));
        this.systemTaskThread.getRequestHeaderUserId(RequestContextUtil.getHeaderMap());
    }

}
