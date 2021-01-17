package com.zhengqing.tool.generator.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 模板文件信息
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 18:06
 */
@Data
@ApiModel("模板文件信息")
public class CgGeneratorCodeTemplateFileBO {

    @ApiModelProperty(value = "模板文件名")
    private String fileName;

    @ApiModelProperty(value = "待生成文件后缀")
    private String generateFileSuffix;

    @ApiModelProperty(value = "模板内容")
    private String templateContent;

    @ApiModelProperty(value = "模板关联包")
    private String templateRePackage;

}
