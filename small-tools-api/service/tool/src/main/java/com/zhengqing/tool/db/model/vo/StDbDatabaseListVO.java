package com.zhengqing.tool.db.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 数据库展示视图
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/4 14:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据库展示视图")
public class StDbDatabaseListVO {

    @ApiModelProperty(value = "数据源名称")
    private String name;

    // @ApiModelProperty(value = "备注")
    // private String dbRemark;

}
