package com.zhengqing.demo.model.dto;

import javax.validation.constraints.NotNull;

import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.validator.fieldrepeat.Update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 测试demo保存提交参数
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2021/01/13 10:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("测试demo保存提交参数")
public class DemoSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {Update.class}, message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

}
