package com.zhengqing.common.config.swagger;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.classmate.TypeResolver;
import com.zhengqing.common.constant.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.readers.operation.AbstractOperationParameterRequestConditionReader;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.List;
import java.util.Set;

import static springfox.documentation.service.Parameter.DEFAULT_PRECEDENCE;

/**
 * <p>
 * swagger - 分页列表api添加分页请求头参数`pageNum`、`pageSize`
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/28 23:10
 */
@Primary
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 10000)
public class SwaggerOperationParameterRequestConditionReader extends AbstractOperationParameterRequestConditionReader {

    private final TypeResolver resolver;

    @Autowired
    public SwaggerOperationParameterRequestConditionReader(TypeResolver resolver) {
        super(resolver);
        this.resolver = resolver;
    }

    @Override
    public void apply(OperationContext context) {
        Set<NameValueExpression<String>> headers = context.headers();
        List<Parameter> parameterList = getParameters(headers, "header");
        if (context.getReturnType().getErasedType() == IPage.class) {
            parameterList.add(new ParameterBuilder().name(AppConstant.PAGE_NUM).description("当前页").defaultValue("1")
                    .required(true).allowMultiple(false).type(resolver.resolve(Integer.class))
                    .modelRef(new ModelRef("Integer")).parameterType("header").order(DEFAULT_PRECEDENCE).build());
            parameterList.add(new ParameterBuilder().name(AppConstant.PAGE_SIZE).description("每页显示数量")
                    .defaultValue("10").required(true).allowMultiple(false).type(resolver.resolve(Integer.class))
                    .modelRef(new ModelRef("Integer")).parameterType("header").order(DEFAULT_PRECEDENCE).build());
        }
        context.operationBuilder().parameters(parameterList);
    }

}
