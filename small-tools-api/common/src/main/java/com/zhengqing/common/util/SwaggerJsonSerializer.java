package com.zhengqing.common.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 * SwaggerJsonSerializer
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 9:54
 */
public class SwaggerJsonSerializer implements ObjectSerializer, ObjectDeserializer {

    public final static SwaggerJsonSerializer INSTANCE = new SwaggerJsonSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
            throws IOException {
        SerializeWriter out = serializer.getWriter();
        Json json = (Json) object;
        out.write(json.value());
    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
