package com.zhengqing.tool.other.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-10-25 13:27:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_st_other_anonymity")
@ApiModel("小工具 - 其它 - 匿名事件表")
public class StOtherAnonymity extends Model<StOtherAnonymity> {

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

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "是否删除：true->删除，false->未删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean isDeleted;

}
