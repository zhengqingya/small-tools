package com.zhengqing.tool.generator.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表展示视图
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
@Data
@ApiModel("代码生成器 - 项目关联数据库表展示视图")
public class CgProjectReDbListVO {

    @ApiModelProperty(value = "主键ID")
    private Integer projectReDbDataSourceId;

    @ApiModelProperty(value = "所属项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "所属项目名")
    private String projectName;

    @ApiModelProperty(value = "数据源id")
    private Integer dbDataSourceId;

    @ApiModelProperty(value = "数据源名称")
    private String dbDataSourceName;

    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
