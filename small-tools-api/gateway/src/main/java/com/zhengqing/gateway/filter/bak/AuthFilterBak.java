//package com.zhengqing.gateway.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zhengqing.common.base.enums.ApiResultCodeEnum;
//import com.zhengqing.common.base.constant.AppConstant;
//import com.zhengqing.common.core.model.bo.JwtUserBO;
//import com.zhengqing.common.core.util.JwtUtil;
//import com.zhengqing.gateway.a.util.ResponseUtil;
//import com.zhengqing.gateway.config.GatewayProperty;
//import com.zhengqing.gateway.config.GatewayProperty.Auth;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * <p>
// * 全局过滤器 - web
// * </p>
// *
// * @author zhengqingya
// * @description
// * @date 2021/1/3 18:38
// */
//@Slf4j
//@Component
//@Order(3)
//public class AuthFilterBak implements GlobalFilter {
//
//    @Resource
//    private GatewayProperty gatewayProperty;
//
//    @Resource
//    private ObjectMapper objectMapper;
//
//    @Override
//    @SneakyThrows(Exception.class)
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String requestUrl = request.getURI().getPath();
//
//        if (this.isSkip(requestUrl)) {
//            return chain.filter(exchange);
//        }
//
//        String tokenValue = request.getHeaders().getFirst(AppConstant.REQUEST_HEADER_TOKEN);
//
//        if (StringUtils.isBlank(tokenValue)) {
//            return ResponseUtil.writeErrorInfo(response, ApiResultCodeEnum.TOKEN_NOT_EXIST);
//        }
//        HttpHeaders headers = new HttpHeaders();
//
//        JwtUserBO tokenInfo = null;
//        try {
//            tokenInfo = JwtUtil.parse(tokenValue);
//        } catch (Exception e) {
//            log.error("解析并验证jwt错误： {}", e);
//            return ResponseUtil.writeErrorInfo(response, ApiResultCodeEnum.TOKEN_EXPIRED);
//        }
//
////        headers.add(AppConstant.CONTEXT_KEY_SYS_USER_ID, String.valueOf(tokenInfo.getUserId()));
////        headers.add(AppConstant.CONTEXT_KEY_SYS_USERNAME, URLEncoder.encode(tokenInfo.getUsername(), "UTF-8"));
//        ServerHttpRequest nowRequest = request.mutate().headers(header -> header.addAll(headers)).build();
//        // 将现在的request 变成 change对象
//        ServerWebExchange build = exchange.mutate().request(nowRequest).build();
//        return chain.filter(build);
//    }
//
//    /**
//     * 安全认证
//     *
//     * @param requestUrl 请求url
//     * @return boolean
//     * @author zhengqingya
//     * @date 2021/1/13 13:49
//     */
//    private boolean isSkip(String requestUrl) {
//        Auth auth = this.gatewayProperty.getAuth();
//        List<String> ignoreUrls = auth.getIgnoreUrls();
//        List<String> openApiUrls = auth.getOpenApiUrls();
//        List<String> webApiUrls = auth.getWebApiUrls();
//
//        boolean ifIgnoreUrl = ignoreUrls.stream().anyMatch(requestUrl::contains);
//        boolean ifOpenApiUrl = openApiUrls.stream().anyMatch(requestUrl::contains);
//        boolean ifWebApiUrl = webApiUrls.stream().anyMatch(requestUrl::contains);
//
//        return ifIgnoreUrl || ifOpenApiUrl || !ifWebApiUrl;
//    }
//
//}
