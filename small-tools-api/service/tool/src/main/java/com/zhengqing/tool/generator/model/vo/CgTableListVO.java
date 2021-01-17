package com.zhengqing.tool.generator.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 表列表信息
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/14 22:42
 */
@Data
@AllArgsConstructor
@ApiModel("表列表信息")
public class CgTableListVO {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "表注释")
    private String tableComment;

}
