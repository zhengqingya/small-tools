package com.zhengqing.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhengqing.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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

    @TableField(value = "sex", updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("性别")
    private Integer sex;

}
