package com.zhengqing.tool.generator.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 包信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/16 22:04
 */
@Data
@ApiModel("包信息")
public class CgGeneratorCodePackageInfoBO {

    @ApiModelProperty(value = "包信息")
    private PackageInfo packageInfo;

    @Data
    @ApiModel("包信息")
    public static class PackageInfo {
        @ApiModelProperty(value = "包名")
        private String packageName;
    }

}
