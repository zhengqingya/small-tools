package com.zhengqing.tool.crawler.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息展示视图
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-08-21 22:35:34
 */
@Data
@ApiModel("小工具 - 爬虫 - 文章信息展示视图")
public class StCrawlerArticleInfoListVO {

    @ApiModelProperty(value = "文章信息主键ID")
    private Integer articleInfoId;

    @ApiModelProperty(value = "网站id(关联表`t_st_crawler_website`字段`website_id`)")
    private Integer websiteId;

    @ApiModelProperty(value = "网站名称")
    private String websiteName;

    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文章所属分类")
    private String category;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章url地址")
    private String url;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "发布时间字符串格式")
    private String publishTimeStr;

}
