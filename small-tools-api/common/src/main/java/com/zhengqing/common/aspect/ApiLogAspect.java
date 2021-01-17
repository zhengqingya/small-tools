package com.zhengqing.common.aspect;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.ServletUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 日志切面
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/8/1 19:09
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void logPointCut() {}

    @Before("logPointCut()")
    public void doAround(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null) {
            int userId =
                ServletUtil.getParameterToInt(AppConstant.CONTEXT_KEY_USER_ID, AppConstant.DEFAULT_CONTEXT_KEY_USER_ID);
            String username =
                ServletUtil.getParameter(AppConstant.CONTEXT_KEY_USERNAME, AppConstant.DEFAULT_CONTEXT_KEY_USERNAME);

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            log.debug("========================== ↓↓↓↓↓↓ 《ApiLogAspect》 Start... ↓↓↓↓↓↓ ==========================");
            log.debug("《ApiLogAspect》 controller method: {}",
                signature.getDeclaringTypeName() + "." + signature.getName());

            // 获取切入点所在的方法
            ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                log.debug("《ApiLogAspect》 controller method description: {}", apiOperation.value());
            }
            log.debug("《ApiLogAspect》 operatorId: {}", userId);
            log.debug("《ApiLogAspect》 operatorName: {}", username);
            log.debug("《ApiLogAspect》 request header: {}", request.getHeader(AppConstant.REQUEST_HEADER_TOKEN));
            log.debug("《ApiLogAspect》 request ip: {}", request.getRemoteAddr());
            log.debug("《ApiLogAspect》 request url: {}", request.getRequestURL().toString());
            log.debug("《ApiLogAspect》 request http method: {}", request.getMethod());
            log.debug("《ApiLogAspect》 request params: {}", this.getRequestValue(request));
            log.debug("========================== ↑↑↑↑↑↑ 《ApiLogAspect》 End... ↑↑↑↑↑↑ ==========================");
        }
    }

    /**
     * 获取请求的参数
     */
    private String getRequestValue(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        String params = JSON.toJSONString(map);
        return StringUtils.substring(params, 0, 2000);
    }

}
