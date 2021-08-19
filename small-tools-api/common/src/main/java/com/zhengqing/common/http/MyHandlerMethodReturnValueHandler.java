package com.zhengqing.common.http;

import com.zhengqing.common.constant.AppConstant;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 封装返回值处理
 * </p>
 *
 * @author zhengqingya
 * @description {@link com.zhengqing.common.http.ApiResult}
 * @date 2020/8/1 18:40
 */
public class MyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler returnValueHandler;

    public MyHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return returnValueHandler.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String servletPath = nativeRequest.getServletPath();
        // 是否处理返回值
        boolean ifHandleReturnValue = true;
        for (String api : AppConstant.RETURN_VALUE_HANDLER_EXCLUDE_API_LIST) {
            if (servletPath.contains(api)) {
                ifHandleReturnValue = false;
                break;
            }
        }
        returnValueHandler.handleReturnValue(ifHandleReturnValue ? ApiResult.ok(returnValue) : returnValue, returnType, mavContainer, webRequest);
    }

}
