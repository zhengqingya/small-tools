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
 * 项目管理查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 11:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("项目管理查询参数")
public class CgProjectListDTO extends BaseDTO {

    @NotNull(message = "关联项目ID为空不能刷新，请关闭弹出窗重新进入！")
    @ApiModelProperty(value = "项目id")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String name;

}
