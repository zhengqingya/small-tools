package com.zhengqing.common.base.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p>
 * 基类查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/9/13 0013 1:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基类查询参数")
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前用户ID", hidden = true)
    // @JSONField: 解决由于json转成类时字段不一致的问题 前端：name值 后端：userId
    @JSONField(name = "userId", serialize = false, deserialize = false)
    private Integer currentUserId;

    @ApiModelProperty(value = "当前用户名称", hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String currentUsername;

    // @ApiModelProperty(value = "令牌", hidden = true)
    // @JSONField(serialize = false, deserialize = false)
    // private String token;

}
