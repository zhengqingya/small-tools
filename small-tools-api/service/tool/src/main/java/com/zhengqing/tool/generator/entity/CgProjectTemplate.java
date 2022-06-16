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
 * 项目代码模板表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 11:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("项目代码模板表")
@TableName("t_cg_project_template")
public class CgProjectTemplate extends IsDeletedYesBaseEntity<CgProjectTemplate> {

    @ApiModelProperty(value = "模板ID")
    @TableId(value = "project_template_id", type = IdType.AUTO)
    private Integer projectTemplateId;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "包ID")
    private Integer projectPackageId;

    @ApiModelProperty(value = "生成文件名 ex:.UserEntity")
    private String fileName;

    @ApiModelProperty(value = "生成文件后缀名 ex:.java")
    private String fileSuffix;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "是否作为基础模板使用（1：是 0：否）")
    private Integer isBasic;

    @ApiModelProperty(value = "关联用户id")
    private Integer dataReUserId;

}
