package com.zhengqing.demo.feign.fallback;

import com.zhengqing.common.exception.MyException;
import com.zhengqing.demo.feign.IDemoClient;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Feign错误处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/4 11:53
 */
@Component
public class IDemoClientFallback implements IDemoClient {

    @Override
    public Long flowCommon() {
        throw new MyException("请求失败！");
    }

}
