//package com.zhengqing.gateway.exception;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhengqing.common.base.model.vo.ApiResult;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.ResourceProperties;
//import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.reactive.function.server.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Map;
//
///**
// * <p>
// * gateway 自定义异常处理
// * </p>
// *
// * @author zhengqingya
// * @description
// * @date 2021/1/13 11:36
// */
//public class ErrorExceptionHandler extends DefaultErrorWebExceptionHandler {
//
//    public ErrorExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
//                                 ErrorProperties errorProperties, ApplicationContext applicationContext) {
//        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
//    }
//
//    /**
//     * 获取异常属性
//     */
//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
//        int code = 500;
//        Throwable error = super.getError(request);
//        if (error instanceof NotFoundException) {
//            code = 404;
//        }
//        if (error instanceof ResponseStatusException) {
//            code = ((ResponseStatusException) error).getStatus().value();
//        }
//        return JSONObject.parseObject(JSONObject.toJSONString(ApiResult.fail(code, this.buildMessage(request, error))),
//                Map.class);
//    }
//
//    /**
//     * 指定响应处理方法为JSON处理的方法
//     */
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
//    }
//
//    /**
//     * 根据status获取对应的HttpStatus
//     */
//    @Override
//    protected int getHttpStatus(Map<String, Object> errorAttributes) {
//        return (int) errorAttributes.get("status");
//    }
//
//    /**
//     * 构建异常信息
//     */
//    private String buildMessage(ServerRequest request, Throwable ex) {
//        StringBuilder message = new StringBuilder("Failed to handle request [");
//        message.append(request.methodName());
//        message.append(" ");
//        message.append(request.uri());
//        message.append("]");
//        if (ex != null) {
//            message.append(": ");
//            message.append(ex.getMessage());
//        }
//        return message.toString();
//    }
//
//}
