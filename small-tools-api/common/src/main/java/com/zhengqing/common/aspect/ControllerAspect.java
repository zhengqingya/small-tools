package com.zhengqing.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.http.ContextHandler;
import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.parameter.ParameterVerify;
import com.zhengqing.common.util.ServletUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Controller 切面
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2021/1/9 17:47
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void controllerPointCut() {}

    /**
     * Before增强：在目标方法被执行的时候织入增强
     *
     * Controller所有方法为切入点
     */
    @Before("controllerPointCut()")
    public void controllerPointCut(JoinPoint joinPoint) {
        // 1、装入当前操作人id&名称
        int userId =
            ServletUtil.getParameterToInt(AppConstant.CONTEXT_KEY_USER_ID, AppConstant.DEFAULT_CONTEXT_KEY_USER_ID);
        String username =
            ServletUtil.getParameter(AppConstant.CONTEXT_KEY_USERNAME, AppConstant.DEFAULT_CONTEXT_KEY_USERNAME);
        ContextHandler.setUserId(userId);
        ContextHandler.setUsername(username);

        // 2、参数处理
        Object[] paramObjArray = joinPoint.getArgs();
        // 遍历所有传入参数,赋值
        for (Object paramObj : paramObjArray) {
            // dto参数处理
            if (paramObj instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO)paramObj;
                baseDTO.setCurrentUserId(userId);
                baseDTO.setCurrentUsername(username);
            }

            // 参数校验处理
            if (paramObj instanceof ParameterVerify) {
                ParameterVerify parameterVerify = (ParameterVerify)paramObj;
                parameterVerify.checkParam();
            }
        }
    }

}
