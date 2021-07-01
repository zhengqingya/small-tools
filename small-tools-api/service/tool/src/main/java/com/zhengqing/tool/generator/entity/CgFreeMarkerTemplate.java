package com.zhengqing.tool.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-02 19:23:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_cg_free_marker_template")
@ApiModel("代码生成器 - FreeMarker模板数据配置表")
public class CgFreeMarkerTemplate extends BaseEntity<CgFreeMarkerTemplate> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "free_marker_template_id", type = IdType.AUTO)
    private Integer freeMarkerTemplateId;

    @ApiModelProperty(value = "键")
    private String templateKey;

    @ApiModelProperty(value = "值")
    private String templateValue;

    @ApiModelProperty(value = "关联人员id")
    private Integer templateReUserId;

    @ApiModelProperty(value = "是否公共使用（1：是 0：否）")
    private Integer isCommon;

}
