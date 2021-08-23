package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 项目关联数据库表列表信息查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/14 22:26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("项目关联数据库表列表信息查询参数")
public class CgProjectReDbTableListDTO extends BaseDTO {

    @NotNull(message = "数据源id不能为空")
    @ApiModelProperty(value = "数据源ID")
    private Integer projectReDbDataSourceId;

    @ApiModelProperty(value = "数据表名")
    private String tableName;

}
