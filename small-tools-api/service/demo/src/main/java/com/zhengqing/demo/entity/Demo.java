package com.zhengqing.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.db.config.mybatis.handler.ListToStrTypeHandler;
import com.zhengqing.common.db.entity.IsDeletedYesBaseEntity;
import com.zhengqing.demo.model.bo.DemoBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

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
@TableName(value = "t_demo", autoResultMap = true)
@ApiModel("测试demo")
public class Demo extends IsDeletedYesBaseEntity<Demo> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    /**
     * {@link UserSexEnum}
     * sex值为空时，MP更新数据库时不忽略此字段值
     */
    @TableField(value = "sex", updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("性别")
    private UserSexEnum sexEnum;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    @ApiModelProperty("数量")
    private Integer num;

    /**
     * 字段类型处理器
     * tips：使用时，必须开启映射注解`@TableName(autoResultMap = true)`否则插入没问题，但查询时该字段会为空
     * 可参考 https://baomidou.com/pages/fd41d8/
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("demoJson")
    private DemoBO demoJson;

    @TableField(typeHandler = ListToStrTypeHandler.class)
    @ApiModelProperty("numJson")
    private List<String> numJson;

}
