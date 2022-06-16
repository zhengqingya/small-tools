package com.zhengqing.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.core.config.interceptor.HandlerInterceptorForTenantId;
import com.zhengqing.common.core.constant.SecurityConstant;
import com.zhengqing.common.core.model.bo.JwtUserBO;
import com.zhengqing.common.core.util.JwtUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.gateway.security.ResourceServerManager;
import com.zhengqing.gateway.util.ResponseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;


/**
 * <p> 安全拦截全局过滤器 </p>
 *
 * @author zhengqingya
 * @description 非网关鉴权的逻辑
 * {@link ResourceServerManager#check(Mono, AuthorizationContext)} 鉴权通过后 -- 部分忽略接口直接放行不走鉴权
 * 将JWT令牌中的用户信息解析出来，然后存入请求的Header中，这样后续服务就不需要解析JWT令牌了，可以直接从请求的Header中获取到用户信息。
 * @date 2022/6/11 5:15 PM
 */
@Slf4j
@Order(2)
@Component
public class AuthFilter implements GlobalFilter {

    @Override
    @SneakyThrows(Exception.class)
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            String token = request.getHeaders().getFirst(SecurityConstant.AUTHORIZATION_KEY);
            if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, SecurityConstant.JWT_PREFIX)) {
                // 部分忽略接口不走鉴权处&没有token 则放行
                return chain.filter(exchange);
            }

            /**
             * 解析token中的用户信息 & 写入请求头里传递给下游
             * 解析上游数据 {@link HandlerInterceptorForTenantId#preHandle}
             */
            JwtUserBO jwtUserBO = JwtUtil.parse(token);
            String userJson = RedisUtil.get(SecurityConstant.JWT_CUSTOM_USER + jwtUserBO.getJti());
            if (StringUtils.isBlank(userJson)) {
                // 校验redis中的token是否过期 -- 即是否注销或其他原因...
                return ResponseUtil.writeErrorInfo(response, ApiResultCodeEnum.TOKEN_EXPIRED);
            }

            HttpHeaders headers = new HttpHeaders();
            // URLEncoder.encode：解决下游获取中文乱码问题
            headers.add(BaseConstant.REQUEST_HEADER_JWT_USER, URLEncoder.encode(userJson, "UTF-8"));
            ServerHttpRequest nowRequest = request.mutate().headers(header -> header.addAll(headers)).build();
            // 将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(nowRequest).build();
            return chain.filter(build);
        } catch (Exception e) {
            log.error("AuthFilter: ", e);
            return ResponseUtil.writeErrorInfo(response, ApiResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
