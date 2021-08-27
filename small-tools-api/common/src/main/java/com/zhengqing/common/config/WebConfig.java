package com.zhengqing.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * WebConfig
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 23:08
 */
@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements ServletContextInitializer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initProperties();
        loadAllUrlMapping(servletContext);
    }

    /**
     * 初始化
     */
    private void initProperties() {
        log.info("============= ↓↓↓↓↓↓ [初始化系统配置参数] ↓↓↓↓↓↓ =============");
    }

    /**
     * 读取系统URL映射
     *
     * @param servletContext
     */
    private void loadAllUrlMapping(ServletContext servletContext) {
        log.info("============= ↓↓↓↓↓↓ [读取系统URL映射] ↓↓↓↓↓↓ =============");
        ApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        Map<String, HandlerMapping> requestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(webApplicationContext, HandlerMapping.class, true, false);
        for (HandlerMapping handlerMapping : requestMappings.values()) {
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (RequestMappingInfo rmi : handlerMethods.keySet()) {
                    PatternsRequestCondition prc = rmi.getPatternsCondition();
                    HandlerMethod handlerMethod = handlerMethods.get(rmi);
                    Set<String> patterns = prc.getPatterns();
                    for (String uStr : patterns) {
                        Method methodItem = handlerMethod.getMethod();
                        log.debug("URL：[{}]  Controller方法：[{}]",
                                uStr,
                                methodItem.getDeclaringClass().getSimpleName() + "." + methodItem.getName());
                    }
                }
            }
        }
    }
}
