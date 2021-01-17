package com.zhengqing.tool.generator.model.vo;

import java.util.List;

import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO.ColumnInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 表字段信息
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/14 22:23
 */
@Data
@ApiModel("表字段信息")
public class CgTableInfoVO {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "表注释")
    private String tableComment;

    @ApiModelProperty(value = "表字段信息")
    private List<ColumnInfo> columnInfoList;

    @ApiModelProperty(value = "可用于检索的字段")
    private List<String> queryColumnList;

    @ApiModelProperty(value = "包名称")
    private String packageName;

}
