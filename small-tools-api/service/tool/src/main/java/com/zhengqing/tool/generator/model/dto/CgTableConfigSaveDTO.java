package com.zhengqing.tool.generator.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zhengqing.common.model.dto.BaseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 代码生成器 - 表配置提交参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 14:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - 表配置提交参数")
public class CgTableConfigSaveDTO extends BaseDTO {

    // @NotNull(groups = {Update.class}, message = "主键ID不能为空!")
    // @ApiModelProperty(value = "主键ID")
    // private Integer id;

    @NotNull(message = "项目id不能为空！")
    @ApiModelProperty(value = "所属项目ID")
    private Integer projectId;

    @NotBlank(message = "表名不能为空！")
    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "用户检索的字段")
    private List<String> queryColumnList;

    @NotNull(message = "数据源ID不能为空！")
    @ApiModelProperty(value = "数据源ID")
    private Integer projectReDbDataSourceId;

    @NotBlank(message = "包名称不能为空！")
    @ApiModelProperty(value = "包名称")
    private String packageName;

    @NotBlank(message = "模块名不能为空！")
    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @NotNull(message = "数据类型不能为空！")
    @ApiModelProperty(value = "数据类型（1：默认数据 2：测试模板生成配置数据）")
    private Integer dataType;

}
