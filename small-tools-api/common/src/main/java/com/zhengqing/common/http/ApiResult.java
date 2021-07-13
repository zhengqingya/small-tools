package com.zhengqing.common.http;

import com.zhengqing.common.context.ContextHandler;
import com.zhengqing.common.enums.ApiResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * API返回参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/7/20 11:09
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "API返回参数")
public class ApiResult<T> {

    @ApiModelProperty(value = "响应码：参考`ResultCode`", required = true)
    private Integer code;

    @ApiModelProperty(value = "消息内容", required = false)
    private String msg;

    @ApiModelProperty(value = "响应数据", required = false)
    private Object data;

    /***
     * 过期
     *
     * @param msg:
     *            消息内容
     * @return com.zhengqing.modules.common.dto.output.ApiResult
     */
    public static ApiResult expired(String msg) {
        return new ApiResult(ApiResultCodeEnum.TOKEN_EXPIRED.getCode(), msg, null);
    }

    public static ApiResult fail(String msg) {
        return new ApiResult(ApiResultCodeEnum.FAILURE.getCode(), msg, null);
    }

    /***
     * 自定义错误返回码
     *
     * @param code:
     *            验证码
     * @param msg:
     *            消息内容
     * @return com.zhengqing.modules.common.dto.output.ApiResult
     */
    public static ApiResult fail(Integer code, String msg) {
        return new ApiResult(code, msg, null);
    }

    public static ApiResult ok(String msg) {
        return new ApiResult(ApiResultCodeEnum.SUCCESS.getCode(), msg, null);
    }

    public static final Object ok(Object obj) {
        // 拉取ContextHandler 上下文提示信息给前端
        Message msg = ContextHandler.getMessage();
        // 支持Controller层直接返回ApiResult
        ApiResult result = new ApiResult(ApiResultCodeEnum.SUCCESS);
        if (obj instanceof ApiResult) {
            result = ((ApiResult) obj);
        } else {
            // 其他obj封装进data,保持返回格式统一
            result.setData(obj);
        }
        // 如果有上下文消息 则拼凑消息体
        String handlerMesssage = msg.toString();
        if (StringUtils.isNotBlank(handlerMesssage)) {
            result.setMsg(result.getMsg() + "\n" + handlerMesssage);
            if (result.getCode() == ApiResultCodeEnum.SUCCESS.getCode()) {
                // 有提示,并且code == 200, 与前端约定提示码变为-1,表示部分操作成功
                // result.setCode(-1);
            }
        }
        return result;
    }

    public static ApiResult ok() {
        return new ApiResult(ApiResultCodeEnum.SUCCESS.getCode(), "OK", null);
    }

    public static ApiResult build(Object data) {
        return new ApiResult(ApiResultCodeEnum.SUCCESS.getCode(), "OK", data);
    }

    public static ApiResult build(Integer code, String msg, Object data) {
        return new ApiResult(ApiResultCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static ApiResult ok(String msg, Object data) {
        return new ApiResult(ApiResultCodeEnum.SUCCESS.getCode(), msg, data);
    }

    /**
     * 自定义返回码
     */
    public static ApiResult ok(Integer code, String msg) {
        return new ApiResult(code, msg);
    }

    /**
     * 自定义
     *
     * @param code：验证码
     * @param msg：返回消息内容
     * @param data：返回数据
     * @return com.zhengqing.modules.common.dto.output.ApiResult
     */
    public static ApiResult ok(Integer code, String msg, Object data) {
        return new ApiResult(code, msg, data);
    }

    public static ApiResult build(Integer code, String msg) {
        return new ApiResult(code, msg, null);
    }

    public ApiResult(Object data) {
        this.code = ApiResultCodeEnum.SUCCESS.getCode();
        this.msg = "OK";
        this.data = data;
    }

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String buildErrorMessage(Throwable ex) {
        StringBuilder msg = new StringBuilder();
        if (ex != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            ex.printStackTrace(writer);
            msg.append(stringWriter.getBuffer());
        }
        return msg.toString();
    }

}
