package com.zhengqing.tool.generator.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代码生成器 - 项目包展示层
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 12:03
 */
@Data
@ApiModel("代码生成器 - 项目包展示层")
public class CgProjectPackageTreeVO {

    @ApiModelProperty(value = "关联项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "包ID")
    private Integer id;

    @ApiModelProperty(value = "包名")
    private String name;

    @ApiModelProperty(value = "父包ID")
    private Integer parentId;

    @ApiModelProperty(value = "父包名称")
    private String parentPackageName;

    @ApiModelProperty(value = "前端所需children值 -> 即子包")
    private List<CgProjectPackageTreeVO> children;

}
