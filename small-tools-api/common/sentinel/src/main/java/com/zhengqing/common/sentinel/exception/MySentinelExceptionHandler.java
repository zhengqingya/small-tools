package com.zhengqing.common.sentinel.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <p> sentinel 统一异常处理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/5/19 10:14
 */
@Component
public class MySentinelExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
        final String msg;
        if (ex instanceof FlowException) {
            msg = "访问频繁，请稍候再试";
        } else if (ex instanceof DegradeException) {
            msg = "系统降级";
        } else if (ex instanceof ParamFlowException) {
            msg = "热点参数限流";
        } else if (ex instanceof SystemBlockException) {
            msg = "系统规则限流或降级";
        } else if (ex instanceof AuthorityException) {
            msg = "授权规则不通过";
        } else {
            msg = "未知限流降级";
        }

        // http状态码
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        /**
         * {@link com.zhengqing.common.base.http.ApiResult#fail(String)}
         */
        response.getWriter().write(
                JSON.toJSONString(
                        new HashMap<String, String>(3) {{
                            this.put("code", "400");
                            this.put("msg", msg);
                            this.put("data", "");
                        }}
                )
        );
    }

}

