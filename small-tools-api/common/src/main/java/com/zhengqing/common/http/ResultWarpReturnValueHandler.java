package com.zhengqing.common.http;

import com.zhengqing.common.constant.AppConstant;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 封装返回值`ApiResult<T>`处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 18:40
 */
public class ResultWarpReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ResultWarpReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String servletPath = nativeRequest.getServletPath();
        boolean ifHandleReturnValue = true;
        for (String api : AppConstant.RPC_CLIENT_URL_LIST) {
            if (servletPath.contains(api)) {
                ifHandleReturnValue = false;
                break;
            }
        }
        delegate.handleReturnValue(ifHandleReturnValue ? ApiResult.ok(returnValue) : returnValue, returnType, mavContainer, webRequest);
    }

}
