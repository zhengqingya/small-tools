package com.zhengqing.tool.db.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 数据库表展示视图
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/6 3:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据库表展示视图")
public class StDbTableListVO {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "表类型-引擎")
    private String engine;

    @ApiModelProperty(value = "行-表数据量")
    private String tableRows;

    @ApiModelProperty(value = "数据长度")
    private String dataLength;

    @ApiModelProperty(value = "自动递增值")
    private String autoIncrement;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "排序规则")
    private String tableCollation;

    @ApiModelProperty(value = "注释")
    private String tableComment;

}
