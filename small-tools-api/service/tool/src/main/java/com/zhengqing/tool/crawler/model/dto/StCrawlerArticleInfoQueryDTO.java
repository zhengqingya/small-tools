package com.zhengqing.tool.crawler.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 文章信息详情查询参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/5 23:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文章信息详情查询参数")
public class StCrawlerArticleInfoQueryDTO {

    @ApiModelProperty(value = "网站id")
    private Integer websiteId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章所属分类")
    private String category;

}
