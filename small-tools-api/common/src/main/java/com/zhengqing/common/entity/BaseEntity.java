package com.zhengqing.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * <p>
 * BaseEntity
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/18 0018 1:30
 */
@Getter
@Setter
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {

    @TableLogic
    @ApiModelProperty(value = "是否删除：true->删除，false->未删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建人id")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Past(message = "创建时间必须是过去时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人id")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    // @Future(message = "修改时间必须是将来时间")
    private Integer updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
