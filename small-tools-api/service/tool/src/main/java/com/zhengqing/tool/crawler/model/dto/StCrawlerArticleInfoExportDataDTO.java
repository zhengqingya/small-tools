package com.zhengqing.tool.crawler.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 小工具 - 爬虫 - 文章导出参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-08-21 22:35:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 爬虫 - 文章导出参数")
public class StCrawlerArticleInfoExportDataDTO {

    @NotNull(message = "文章id不能为空！")
    @ApiModelProperty(value = "文章id")
    private Integer articleInfoId;

    @NotNull(message = "导出类型不能为空！")
    @ApiModelProperty(value = "导出类型")
    private Integer type;

}
