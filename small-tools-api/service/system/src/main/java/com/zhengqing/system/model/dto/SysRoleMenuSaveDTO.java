package com.zhengqing.system.model.dto;

import com.zhengqing.common.validator.fieldrepeat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 * 保存角色菜单权限信息传入参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/14 11:15
 */
@Data
@ApiModel("保存角色菜单权限信息传入参数")
public class SysRoleMenuSaveDTO {

    @NotNull(message = "角色id不能为空！", groups = Update.class)
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色可访问的菜单ids")
    private List<Integer> menuIdList;

}
