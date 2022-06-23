package com.zhengqing.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.zhengqing.common.base.util.MyDateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 网关请求响应日志打印 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/12 9:13 PM
 */
@Slf4j
@Component
@ConditionalOnProperty(
        value = {"small-tools.gateway-log"},
        havingValue = "true",
        // true表示缺少此配置属性时也会加载该bean
        matchIfMissing = true
)
public class GatewayLogFilter implements GlobalFilter, Ordered {

    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().pathWithinApplication().value();
        String requestMethod = request.getMethodValue();

        TraceLog traceLog = new TraceLog();
        traceLog.setRequestPath(requestPath);
        traceLog.setRequestMethod(requestMethod);
        traceLog.setRequestTime(MyDateUtil.nowStr(MyDateUtil.DATE_TIME_MS_FORMAT));

        MediaType contentType = request.getHeaders().getContentType();

        if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)
                || MediaType.APPLICATION_JSON.isCompatibleWith(contentType)
        ) {
            return this.writeBodyLog(exchange, chain, traceLog);
        } else {
            return this.writeLog(exchange, chain, traceLog);
        }
    }

    public Mono<Void> writeLog(ServerWebExchange exchange, GatewayFilterChain chain, TraceLog traceLog) {
        traceLog.setQueryParams(this.getQueryParams(exchange.getRequest().getQueryParams()));
        traceLog.printRequestStr();
        ServerHttpResponseDecorator serverHttpResponseDecorator = this.serverHttpResponseDecorator(exchange, traceLog);
        return chain.filter(exchange.mutate().response(serverHttpResponseDecorator).build())
                .then(Mono.fromRunnable(() -> traceLog.printResponseStr()));
    }


    /**
     * 解决 request body 只能读取一次问题，
     */
    public Mono<Void> writeBodyLog(ServerWebExchange exchange, GatewayFilterChain chain, TraceLog traceLog) {
        ServerRequest serverRequest = ServerRequest.create(exchange, this.messageReaders);
        traceLog.setQueryParams(this.getQueryParams(exchange.getRequest().getQueryParams()));

        Mono<String> cachedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
            traceLog.setRequestBody(body);
            return Mono.just(body);
        }).doFinally(body -> traceLog.printRequestStr());

        BodyInserter bodyInserter = BodyInserters.fromPublisher(cachedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

        return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
            ServerHttpRequest serverHttpRequest = this.serverHttpRequestDecorator(exchange, headers, outputMessage);
            ServerHttpResponseDecorator serverHttpResponseDecorator = this.serverHttpResponseDecorator(exchange, traceLog);
            return chain.filter(exchange.mutate().request(serverHttpRequest).response(serverHttpResponseDecorator).build())
                    .then(Mono.fromRunnable(() -> traceLog.printResponseStr()));
        }));
    }

    /**
     * 获取请求参数
     */
    private String getQueryParams(MultiValueMap<String, String> queryParams) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String val = entry.getValue().stream().map(String::valueOf).collect(Collectors.joining(","));
            sb.append(entry.getKey()).append("=").append(val).append("&");
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return null;
    }


    /**
     * 请求结果读取
     */
    private ServerHttpRequestDecorator serverHttpRequestDecorator(ServerWebExchange exchange,
                                                                  HttpHeaders headers,
                                                                  CachedBodyOutputMessage outputMessage
    ) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                httpHeaders.setContentLength(headers.getContentLength());
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }


    /**
     * 响应结果读取
     */
    private ServerHttpResponseDecorator serverHttpResponseDecorator(ServerWebExchange exchange, TraceLog traceLog) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    traceLog.setResponseTime(MyDateUtil.nowMsStr());
                    traceLog.setExecuteTime(MyDateUtil.diffMillisecond(
                            MyDateUtil.strToDate(traceLog.getRequestTime(), MyDateUtil.DATE_TIME_MS_FORMAT),
                            MyDateUtil.strToDate(traceLog.getResponseTime(), MyDateUtil.DATE_TIME_MS_FORMAT)
                    ));
                    String originalResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                    if (StrUtil.isNotBlank(originalResponseContentType)
                            && originalResponseContentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            DefaultDataBuffer dataBuffer = new DefaultDataBufferFactory().join(dataBuffers);
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            String responseBody = new String(content, StandardCharsets.UTF_8);
                            traceLog.setResponseBody(responseBody);
                            return bufferFactory.wrap(content);
                        }));
                    }
                }
                return super.writeWith(body);
            }
        };
    }

    @Override
    public int getOrder() {
        return -1;
    }


    @Data
    public static class TraceLog {

        /**
         * 请求路径
         */
        private String requestPath;

        /**
         * 请求方法
         */
        private String requestMethod;

        /**
         * 查询参数
         */
        private String queryParams;

        /**
         * 请求载荷
         */
        private String requestBody;

        /**
         * 响应数据
         */
        private String responseBody;

        /**
         * 请求时间
         */
        private String requestTime;

        /**
         * 响应时间
         */
        private String responseTime;

        /**
         * 执行耗时(毫秒)
         */
        private Long executeTime;


        public void printRequestStr() {
            System.err.println("\n========================== ↓↓↓↓↓↓ 《请求日志》 ↓↓↓↓↓↓ ==========================\n" +
                    this.requestMethod + ':' + this.requestPath + '\n' +
                    "请求时间:" + this.requestTime + '\n' +
                    "查询参数:" + this.queryParams + '\n' +
                    "请求载荷:" + this.requestBody
            );
        }

        public void printResponseStr() {
            System.err.println("\n========================== ↓↓↓↓↓↓ 《响应日志》 ↓↓↓↓↓↓ ==========================\n" +
                    this.requestMethod + ':' + this.requestPath + '\n' +
                    "请求时间:" + this.requestTime + '\n' +
                    "响应时间:" + this.responseTime + '\n' +
                    "响应数据:" + this.responseBody + '\n' +
                    "执行耗时:" + this.executeTime + "毫秒"
            );
        }

        @Override
        public String toString() {
            return "========================== 《网关请求响应日志》 ==========================\n" +
                    "请求路径:" + this.requestPath + '\n' +
                    "请求方法:" + this.requestMethod + '\n' +
                    "请求参数:" + this.requestBody + '\n' +
                    "响应数据:" + this.responseBody + '\n' +
                    "请求时间:" + this.requestTime + '\n' +
                    "响应时间:" + this.responseTime + '\n' +
                    "执行耗时:" + this.executeTime + "毫秒";
        }
    }

}
