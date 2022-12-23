package com.zhengqing.im.api;

import com.zhengqing.common.im.model.ImLoginDTO;
import com.zhengqing.common.im.model.ImMsgDTO;
import com.zhengqing.common.im.util.ImUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("")
@RequiredArgsConstructor
@Api(tags = "test")
public class TestController {

    private final ImUtil imUtil;

    @PostMapping("/login")
    public String login(@RequestBody ImLoginDTO params) {
        this.imUtil.login(params);
        return "OK";
    }

    @PostMapping("/sengMsg")
    public String sengMsg(@RequestBody ImMsgDTO params) {
        this.imUtil.sengMsg(params);
        return "OK";
    }

}
