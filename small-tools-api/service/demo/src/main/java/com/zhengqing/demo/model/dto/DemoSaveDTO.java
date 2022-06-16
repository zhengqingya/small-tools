package com.zhengqing.demo.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.demo.model.bo.DemoBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 测试demo保存提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("测试demo保存提交参数")
public class DemoSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Long id;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "性别", example = "1")
    private Byte sex;

    @Range(min = 1, max = 1, message = "range: 1-3")
    @ApiModelProperty(value = "类型", example = "1")
    private Integer type;

    //    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT)
    //    @JSONField(format = MyDateUtil.DATE_TIME_FORMAT)
    @ApiModelProperty(value = "开始时间", example = "2021-08-25 00:00:00")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "2021-10-25 23:59:59")
    private Date endTime;

    @ApiModelProperty("json")
    private DemoBO demoJson;

    @ApiModelProperty("numJson")
    private List<String> numJson;

}
