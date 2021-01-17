package com.zhengqing.tool.db.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表展示视图
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-09-02 14:45:55
 */
@Data
@ApiModel("小工具 - 数据库 - 数据源配置信息表展示视图")
public class StDbDataSourceListVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "数据源名称")
    private String name;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "端口")
    private String port;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "数据库类型(关联表`t_sys_dict`字段`st_db_data_source_type`分类value值)")
    private String type;

    @ApiModelProperty(value = "数据库类型值")
    private String typeName;

    @ApiModelProperty(value = "驱动程序")
    private String driverClassName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
