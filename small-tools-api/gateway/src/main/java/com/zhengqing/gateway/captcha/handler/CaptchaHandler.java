package com.zhengqing.gateway.captcha.handler;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.IdUtil;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.redis.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> 验证码处理器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/10 17:58
 */
@Component
public class CaptchaHandler implements HandlerFunction<ServerResponse> {

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(300, 100, 4, 4);
        // 1、获取到4位数的验证码
        String captchaCode = shearCaptcha.getCode();
        // TODO 开发阶段临时使用
//        String captchaCode = "666";
        // 生成图片，获取base64编码字符串
        String captchaImageBase64Str = shearCaptcha.getImageBase64Data();

        // 2、缓存验证码至Redis，默认120秒过期
        String uuid = IdUtil.simpleUUID();
        RedisUtil.setEx(SecurityConstant.CAPTCHA_CODE + uuid, captchaCode, 120, TimeUnit.SECONDS);

        // 3、返回
        Map<String, String> result = new HashMap<>(2);
        result.put("uuid", uuid);
        result.put("img", captchaImageBase64Str);
        return ServerResponse.ok().body(BodyInserters.fromValue(ApiResult.ok(result)));
    }
}
