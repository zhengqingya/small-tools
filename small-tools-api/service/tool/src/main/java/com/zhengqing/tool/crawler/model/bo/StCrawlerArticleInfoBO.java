package com.zhengqing.tool.crawler.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 文章信息类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/7 15:54
 */
@Data
@ApiModel("文章信息类")
public class StCrawlerArticleInfoBO {

    @ApiModelProperty(value = "文章信息主键ID")
    private String articleInfoId;

    @ApiModelProperty(value = "网站id(关联表`t_st_crawler_website`字段`website_id`)")
    private String websiteId;

    @ApiModelProperty(value = "网站名称")
    private String websiteName;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文章所属分类")
    private String category;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章url地址")
    private String url;

    @ApiModelProperty(value = "发布时间")
    private String publishTime;

    @ApiModelProperty(value = "发布时间字符串格式")
    private String publishTimeStr;

}
