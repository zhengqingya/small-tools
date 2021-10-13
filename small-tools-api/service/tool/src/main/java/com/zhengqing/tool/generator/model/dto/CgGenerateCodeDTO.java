package com.zhengqing.tool.generator.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhengqing.common.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 代码生成传入参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/8 19:19
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成传入参数")
public class CgGenerateCodeDTO extends BaseDTO {

    @NotNull(message = "数据源id不能为空")
    @ApiModelProperty(value = "数据源ID")
    private Integer projectReDbDataSourceId;

    @NotBlank(message = "数据库表名不能为空！")
    @ApiModelProperty(value = "数据库表名")
    private String tableName;

    @ApiModelProperty(value = "可用于检索的字段")
    private List<String> queryColumnList;

    @NotBlank(message = "父包名称不能为空!")
    @ApiModelProperty(value = "包名称")
    private String packageName;

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @NotNull(message = "数据类型不能为空！")
    @ApiModelProperty(value = "数据类型（1：默认数据 2：测试模板生成配置数据）")
    private Integer dataType;

    @ApiModelProperty(value = "是否为测试模板生成数据（true: 是 false: 不是）", hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private Boolean ifTestTemplateData;

    @ApiModelProperty(value = "模板ID (测试模板生成数据时使用)", hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private Integer projectTemplateId;

    // @ApiModelProperty(value = "是否只是校验模板数据正确性")
    // private Boolean isCheckTemplateData;

}
