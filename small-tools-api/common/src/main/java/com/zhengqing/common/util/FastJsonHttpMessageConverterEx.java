package com.zhengqing.common.util;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import springfox.documentation.spring.web.json.Json;

/**
 * <p>
 * FastJsonHttpMessageConverterEx
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 9:54
 */
public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {

    public FastJsonHttpMessageConverterEx() {
        super();
        this.getFastJsonConfig().getSerializeConfig().put(Json.class, SwaggerJsonSerializer.INSTANCE);
    }

}
