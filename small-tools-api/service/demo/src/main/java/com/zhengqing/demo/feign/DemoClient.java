package com.zhengqing.demo.feign;

import cn.hutool.core.util.RandomUtil;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.feign.rpc.IBaseClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * Feign接口类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
@Slf4j
@ApiIgnore()
@RestController
@RequestMapping(IBaseClient.API_PREFIX + "/" + AppConstant.RPC_DEMO + "/sentinel")
@AllArgsConstructor
public class DemoClient implements IDemoClient {

    @Override
    public Long flowCommon() {
        return RandomUtil.randomLong();
    }

}
