package com.zhengqing.gateway.captcha.router;

import com.zhengqing.gateway.captcha.handler.CaptchaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * <p> 验证码路由 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/15 14:36
 */
@Configuration
public class CaptchaRouter {

    /**
     * http://localhost:1218/captcha
     */
    @Bean
    public RouterFunction<ServerResponse> captchaRouterFunction(CaptchaHandler captchaHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/captcha").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), captchaHandler::handle
        );
    }

}
