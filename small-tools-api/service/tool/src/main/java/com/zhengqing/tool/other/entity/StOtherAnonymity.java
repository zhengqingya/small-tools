package com.zhengqing.tool.other.entity;

import java.util.Date;

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
 * 小工具 - 其它 - 匿名事件表
 * </p>
 *
 * @author: zhengqing
 * @description :
 * @date: 2020-10-25 13:27:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_st_other_anonymity")
@ApiModel("小工具 - 其它 - 匿名事件表")
public class StOtherAnonymity extends BaseEntity<StOtherAnonymity> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "匿名用户名")
    private String anonymousUserName;

    @ApiModelProperty(value = "匿名处理人")
    private String anonymousHandlerName;

    @ApiModelProperty(value = "状态：是否处理 （0:未处理  1:已处理）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "处理时间")
    private Date handleTime;

    @ApiModelProperty(value = "创建人id")
    @TableField(value = "create_by", exist = false)
    private Integer createBy;

    @ApiModelProperty(value = "更新人id")
    @TableField(value = "update_by", exist = false)
    private Integer updateBy;

}
