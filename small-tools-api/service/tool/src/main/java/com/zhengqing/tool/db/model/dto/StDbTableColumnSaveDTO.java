package com.zhengqing.tool.db.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 数据库表字段信息保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/6 20:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据库表字段信息保存参数")
public class StDbTableColumnSaveDTO {

    @NotNull(message = "数据源id不能为空!")
    @ApiModelProperty(value = "数据源id")
    private Integer dataSourceId;

    @NotBlank(message = "数据库名不能为空!")
    @ApiModelProperty(value = "数据库名")
    private String dbName;

    @NotBlank(message = "表名不能为空!")
    @ApiModelProperty(value = "表名")
    private String tableName;

    @NotBlank(message = "表注释不能为空!")
    @ApiModelProperty(value = "表注释")
    private String tableComment;

    @Valid
    @ApiModelProperty(value = "表字段信息")
    List<ColumnInfo> columnInfoList;

    @Data
    @ApiModel("表字段信息")
    public static class ColumnInfo {

        @NotBlank(message = "字段名不能为空!")
        @ApiModelProperty(value = "字段名")
        private String columnName;

        @NotBlank(message = "注释不能为空!")
        @ApiModelProperty(value = "注释")
        private String columnComment;

        @NotBlank(message = "数据类型不能为空!")
        @ApiModelProperty(value = "数据类型")
        private String columnType;

        @NotNull(message = "请选择是否允许空值!")
        @ApiModelProperty(value = "是否允许空值")
        private boolean ifNullAble;

        @NotNull(message = "请选择是否是主键!")
        @ApiModelProperty(value = "是否是主键")
        private boolean ifPrimaryKey;

        @NotNull(message = "请选择是否自增长!")
        @ApiModelProperty(value = "是否自增长(值为`auto_increment`时)")
        private boolean ifAutoIncrement;

        @ApiModelProperty(value = "默认值")
        private String columnDefaultValue;

    }

}
