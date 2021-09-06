package com.zhengqing.system.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 系统管理-系统属性-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-系统属性-保存-提交参数")
public class SysPropertySaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private String id;

    @ApiModelProperty("属性key")
    private String key;

    @ApiModelProperty("属性value")
    private String value;

    @ApiModelProperty("备注")
    private String remark;


}
