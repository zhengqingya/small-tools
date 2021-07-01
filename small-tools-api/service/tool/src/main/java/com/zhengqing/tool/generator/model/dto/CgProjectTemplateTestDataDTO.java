package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代码生成器 - 项目模板数据测试提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/3 21:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - 项目模板数据测试提交参数")
public class CgProjectTemplateTestDataDTO extends BaseDTO {

    // 这里字段改动是注意 对应 `CgProjectTemplateSaveDTO`

    @NotNull(message = "请先保存模板再测试生成数据！")
    @ApiModelProperty(value = "模板ID")
    private Integer projectTemplateId;

    @ApiModelProperty(value = "项目ID")
    @NotNull(message = "请选择关联项目!")
    private Integer projectId;

    @ApiModelProperty(value = "包ID")
    @NotNull(message = "请选择模板类型!")
    private Integer projectPackageId;

    @ApiModelProperty(value = "生成文件名 ex:.UserEntity")
    @NotBlank(message = "生成文件名不能为空!  例如: \"UserEntity\" ")
    private String fileName;

    @ApiModelProperty(value = "生成文件后缀名 ex:.java")
    @NotBlank(message = "生成文件后缀名不能为空!  例如: \".java\" ")
    private String fileSuffix;

    @ApiModelProperty(value = "模板内容")
    @NotBlank(message = "模板内容不能为空!")
    private String content;

    @NotNull(message = "是否作为基础模板使用（1：是 0：否） 不能为空！")
    @ApiModelProperty(value = "是否作为基础模板使用（1：是 0：否）")
    private Integer isBasic;

}
