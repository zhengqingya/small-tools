package com.zhengqing.common.core.aspect;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Service 切面
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/8 22:44
 */
@Slf4j
@Aspect
@Component
// @EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ServiceAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Service.*(..))")
    public void servicePointCut() {
    }

    /**
     * Before增强：在目标方法被执行的时候织入增强
     * <p>
     * Service所有方法作为切入点
     */
    @Before("servicePointCut()")
    public void servicePointCut(JoinPoint joinPoint) {
        // DTO参数处理
        Object[] paramObjArray = joinPoint.getArgs();
        // 遍历所有传入参数,赋值
        for (Object paramObj : paramObjArray) {
            // dto参数处理
            if (paramObj instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) paramObj;
                baseDTO.setCurrentUserId(Integer.valueOf(JwtUtil.getUserId()));
                baseDTO.setCurrentUsername(JwtUtil.getUsername());
            }
        }
    }

}
