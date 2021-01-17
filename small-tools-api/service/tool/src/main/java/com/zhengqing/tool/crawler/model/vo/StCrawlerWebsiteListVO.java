package com.zhengqing.tool.crawler.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理展示视图
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-08-21 22:19:19
 */
@Data
@ApiModel("小工具 - 爬虫 - 网站管理展示视图")
public class StCrawlerWebsiteListVO {

    @ApiModelProperty(value = "网站ID")
    private Integer websiteId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "文章总数")
    private Integer articleSum;

}
