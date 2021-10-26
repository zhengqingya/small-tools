package com.zhengqing.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhengqing.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 测试demo
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_demo")
@ApiModel("测试demo")
public class Demo extends BaseEntity<Demo> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    /**
     * sex值为空时，MP更新数据库时不忽略此字段值
     */
    @TableField(value = "sex", updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("备注")
    private String remark;

}
