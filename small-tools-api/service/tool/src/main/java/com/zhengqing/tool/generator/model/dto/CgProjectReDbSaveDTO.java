package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代码生成器 - 项目关联数据库提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - 项目关联数据库提交参数")
public class CgProjectReDbSaveDTO extends BaseDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    @ApiModelProperty(value = "主键ID")
    private Integer projectReDbDataSourceId;

    @NotNull(message = "项目id不能为空！")
    @ApiModelProperty(value = "所属项目ID")
    private Integer projectId;

    @NotNull(message = "数据源id不能为空！")
    @ApiModelProperty(value = "数据源id")
    private Integer dbDataSourceId;

    @NotBlank(message = "数据库名称不能为空！")
    @ApiModelProperty(value = "库名")
    private String dbName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
