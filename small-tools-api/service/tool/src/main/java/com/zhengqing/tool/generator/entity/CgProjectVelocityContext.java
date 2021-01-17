package com.zhengqing.tool.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 代码生成器 - 项目 - 模板数据源
 * </p>
 *
 * @author: zhengqing
 * @date: 2019-09-17 14:34:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("代码生成器 - 项目 - 模板数据源")
@TableName("t_cg_project_velocity_context")
public class CgProjectVelocityContext extends BaseEntity<CgProjectVelocityContext> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "模板数据源")
    private String velocity;

    @ApiModelProperty(value = "内容")
    private String context;

    @ApiModelProperty(value = "所属项目")
    private Integer projectId;

    @TableField(exist = false)
    private Object contextJsonObject;

}
