package com.zhengqing.demo.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 测试demo
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Data
@ApiModel
public class DemoBO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("爱好")
    private String happy;

}
