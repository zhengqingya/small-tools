package com.zhengqing.tool.generator.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代码生成器 - 项目模板展示
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 15:33
 */
@Data
@ApiModel("代码生成器 - 项目模板展示层")
public class CgProjectTemplateListVO {

    @ApiModelProperty(value = "模板ID")
    private Integer projectTemplateId;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "包ID")
    private Integer projectPackageId;

    @ApiModelProperty(value = "生成文件名 ex:.UserEntity")
    private String fileName;

    @ApiModelProperty(value = "生成文件后缀名 ex:.java")
    private String fileSuffix;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "是否作为基础模板使用（1：是 0：否）")
    private Integer isBasic;

    @ApiModelProperty(value = "关联项目名")
    private String projectName;

    @ApiModelProperty(value = "包名")
    private String packageName;

}
