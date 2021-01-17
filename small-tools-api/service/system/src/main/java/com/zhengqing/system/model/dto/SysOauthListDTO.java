package com.zhengqing.system.model.dto;

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
 * 系统管理 - 用户三方授权列表查询参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/12/6 13:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("系统管理 - 用户三方授权列表查询参数")
public class SysOauthListDTO extends BaseDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}
