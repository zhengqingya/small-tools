package com.zhengqing.tool.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedYesBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 项目数据库信息表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 14:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("项目数据库信息表")
@TableName("t_cg_table_config")
public class CgTableConfig extends IsDeletedYesBaseEntity<CgTableConfig> {

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "所属项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "用户检索的字段")
    private String queryColumns;

    @ApiModelProperty(value = "数据源ID")
    private Integer projectReDbDataSourceId;

    @ApiModelProperty(value = "包名称")
    private String packageName;

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "所属用户ID")
    private Integer dataReUserId;

    @ApiModelProperty(value = "数据类型（1：默认数据 2：测试模板生成配置数据）")
    private Integer dataType;

}
