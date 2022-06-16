package com.zhengqing.tool.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedYesBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:35:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("小工具 - 爬虫 - 文章信息")
@TableName("t_st_crawler_article_info")
public class StCrawlerArticleInfo extends IsDeletedYesBaseEntity<StCrawlerArticleInfo> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "article_info_id", type = IdType.AUTO)
    private Integer articleInfoId;

    @ApiModelProperty(value = "网站id(关联表`t_st_crawler_website`字段`website_id`)")
    private Integer websiteId;

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

}
