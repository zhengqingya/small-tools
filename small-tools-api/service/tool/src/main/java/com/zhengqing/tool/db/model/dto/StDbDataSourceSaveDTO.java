package com.zhengqing.tool.db.model.dto;

import com.zhengqing.common.validator.fieldrepeat.FieldRepeatValidator;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-09-02 14:45:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 数据库 - 数据源配置信息表提交参数")
@FieldRepeatValidator(tableName = "t_st_db_data_source", fieldNames = {"name"}, dbFieldNames = {"name"},
        message = "数据库名称重复，请重新输入！")
public class StDbDataSourceSaveDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空!")
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @NotBlank(message = "数据源名称不能为空!")
    @ApiModelProperty(value = "数据源名称")
    private String name;

    @NotBlank(message = "ip地址不能为空!")
    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @NotBlank(message = "端口不能为空!")
    @ApiModelProperty(value = "端口")
    private String port;

    @NotBlank(message = "用户名不能为空!")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空!")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "请选择数据库类型!")
    @ApiModelProperty(value = "数据库类型(关联表`t_sys_dict`字段`st_db_data_source_type`分类value值)")
    private String type;

    @ApiModelProperty(value = "驱动程序")
    private String driverClassName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
