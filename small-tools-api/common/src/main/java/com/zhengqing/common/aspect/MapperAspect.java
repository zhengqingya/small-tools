package com.zhengqing.common.aspect;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.ParameterException;
import com.zhengqing.common.util.ServletUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Mapper分页参数注入切面
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 18:18
 */
@Aspect
@Order(2)
@Component
public class MapperAspect {

    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Mapper.*(..))")
    public void mapperPointCut() {
    }

    /**
     * Before增强：在目标方法被执行的时候织入增强
     * <p>
     * Mapper所有方法作为切入点
     */
    @Before("mapperPointCut()")
    public void mapperPointCut(JoinPoint joinPoint) {
        Object[] paramObjArray = joinPoint.getArgs();
        int pageNum = ServletUtil.getParameterToInt(AppConstant.PAGE_NUM, DEFAULT_PAGE_NUM);
        int pageSize = ServletUtil.getParameterToInt(AppConstant.PAGE_SIZE, DEFAULT_PAGE_SIZE);
        // 遍历所有传入参数,赋值
        for (Object paramObj : paramObjArray) {
            if (paramObj instanceof IPage) {
                if (pageNum < 1 || pageSize < 1) {
                    throw new ParameterException("传递的分页参数有误!");
                }
                IPage<?> page = ((IPage<?>) paramObj);
                page.setCurrent(pageNum);
                page.setSize(pageSize);
            }
        }
    }

}
