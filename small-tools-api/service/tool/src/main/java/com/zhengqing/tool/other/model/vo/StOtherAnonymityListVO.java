package com.zhengqing.tool.other.model.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表展示视图
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-10-25 13:27:16
 */
@Data
@ApiModel("小工具 - 其它 - 匿名事件表展示视图")
public class StOtherAnonymityListVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "匿名用户名")
    private String anonymousUserName;

    @ApiModelProperty(value = "匿名处理人")
    private String anonymousHandlerName;

    @ApiModelProperty(value = "状态：是否处理 （0:未处理  1:已处理）")
    private Integer status;

    @ApiModelProperty(value = "状态值")
    private String statusName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "处理时间")
    private Date handleTime;

}
