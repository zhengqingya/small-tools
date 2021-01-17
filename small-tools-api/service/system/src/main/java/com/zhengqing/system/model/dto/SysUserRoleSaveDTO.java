package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 * 保存用户角色参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/10 14:19
 */
@Data
@ApiModel("保存用户角色参数")
public class SysUserRoleSaveDTO {

    @NotNull(message = "用户id不能为空！")
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("角色ids")
    private List<Integer> roleIdList;

}
