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
 * 代码生成器 - 项目包管理表
 * </p>
 *
 * @author zhengqingya
 * @date 2019-09-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("代码生成器 - 项目包管理表")
@TableName("t_cg_project_package")
public class CgProjectPackage extends IsDeletedYesBaseEntity<CgProjectPackage> {

    @ApiModelProperty(value = "包ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "包名")
    private String name;

    @ApiModelProperty(value = "父包ID")
    private Integer parentId;

    @ApiModelProperty(value = "包关联项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "关联用户id")
    private Integer dataReUserId;

}
