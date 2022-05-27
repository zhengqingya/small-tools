package com.zhengqing.tool.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_cg_project_re_db")
@ApiModel("代码生成器 - 项目关联数据库表")
public class CgProjectReDb extends BaseEntity<CgProjectReDb> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "project_re_db_data_source_id", type = IdType.AUTO)
    private Integer projectReDbDataSourceId;

    @ApiModelProperty(value = "所属项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "数据源id")
    private Integer dbDataSourceId;

    @ApiModelProperty(value = "库名")
    private String dbName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "所属用户id")
    private Integer dataReUserId;

}
