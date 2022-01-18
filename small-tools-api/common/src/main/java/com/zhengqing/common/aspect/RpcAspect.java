package com.zhengqing.common.aspect;

import com.zhengqing.common.parameter.ParameterVerify;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p> rpc 切面 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/23 19:01
 */
@Slf4j
@Aspect
@Component
public class RpcAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.feign.*(..))")
    public void rpcPointCut() {
    }

    /**
     * Before增强：在目标方法被执行的时候织入增强
     * rpc所有方法为切入点
     */
    @Before("rpcPointCut()")
    public void rpcPointCut(JoinPoint joinPoint) {
        // 参数处理
        Object[] paramObjArray = joinPoint.getArgs();
        // 遍历所有传入参数,赋值
        for (Object paramObj : paramObjArray) {
            // 参数校验处理
            if (paramObj instanceof ParameterVerify) {
                ParameterVerify parameterVerify = (ParameterVerify) paramObj;
                parameterVerify.checkParam();
            }
        }
    }

}
