package com.zhengqing.common.custom.lock;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * <p> aop切面-redisson分布式锁 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/18 14:24
 */
@Slf4j
@Aspect
@Component
@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class RedisLockAspect {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 表达式解析器
     */
    private final ExpressionParser parser = new SpelExpressionParser();
    
    /**
     * 获取方法的参数名
     */
    private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Around("@annotation(com.zhengqing.common.custom.lock.RedisLock)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String key = redisLock.key();
        Object[] args = pjp.getArgs();
        key = this.parse(key, method, args);
        // 获取锁
        RLock lock = this.getLock(key, redisLock);
        lock.lock(redisLock.leaseTime(), redisLock.unit());
        try {
            return pjp.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    /**
     * 解析spring EL表达式
     *
     * @param key    表达式
     * @param method 方法
     * @param args   方法参数
     * @return java.lang.String
     * @author zhengqingya
     * @date 2022/1/18 14:35
     */
    private String parse(String key, Method method, Object[] args) {
        // 获取方法的参数名
        String[] paramNameArray = this.discoverer.getParameterNames(method);
        if (paramNameArray == null) {
            return "";
        }
        // 表达式的上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArray.length; i++) {
            // 为了让表达式可以访问该对象, 先把对象放到上下文中
            context.setVariable(paramNameArray[i], args[i]);
        }
        return this.parser.parseExpression(key).getValue(context, String.class);
    }

    /**
     * 获取锁
     *
     * @param key       key
     * @param redisLock 分布式锁注解
     * @return 锁
     * @author zhengqingya
     * @date 2022/1/18 14:28
     */
    private RLock getLock(String key, RedisLock redisLock) {
        switch (redisLock.lockType()) {
            case REENTRANT_LOCK:
                return this.redissonClient.getLock(key);
            case FAIR_LOCK:
                return this.redissonClient.getFairLock(key);
            case READ_LOCK:
                return this.redissonClient.getReadWriteLock(key).readLock();
            case WRITE_LOCK:
                return this.redissonClient.getReadWriteLock(key).writeLock();
            default:
                throw new RuntimeException("do not support lock type:" + redisLock.lockType().name());
        }
    }

}
