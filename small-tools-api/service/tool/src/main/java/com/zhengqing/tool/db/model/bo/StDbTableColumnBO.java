package com.zhengqing.tool.db.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 数据库表字段信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/8 16:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据库表字段信息")
public class StDbTableColumnBO {

    @ApiModelProperty(value = "数据库名")
    private String dbName;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "表注释")
    private String tableComment;

    @ApiModelProperty(value = "表字段信息")
    List<ColumnInfo> columnInfoList;

    @Data
    @ApiModel("表字段信息")
    public static class ColumnInfo {

        @ApiModelProperty(value = "字段名")
        private String columnName;

        @ApiModelProperty(value = "注释")
        private String columnComment;

        @ApiModelProperty(value = "数据类型")
        private String columnType;

        @ApiModelProperty(value = "是否允许空值")
        private boolean ifNullAble;

        @ApiModelProperty(value = "是否是主键(值为`PRI`时)")
        private boolean ifPrimaryKey;

        @ApiModelProperty(value = "是否自增长(值为`auto_increment`时)")
        private boolean ifAutoIncrement;

        @ApiModelProperty(value = "默认值")
        private String columnDefaultValue;

    }

}
