package com.zhengqing.tool.generator.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 项目展示视图
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/8/22 11:13
 */
@Data
@ApiModel("项目展示视图")
public class CgProjectListVO {

    @ApiModelProperty(value = "项目ID")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "所属用户ID")
    private Integer dataReUserId;

    @ApiModelProperty(value = "项目所属用户名")
    private String username;

}
