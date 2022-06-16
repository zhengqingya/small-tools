package com.zhengqing.tool.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedYesBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:19:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("小工具 - 爬虫 - 网站管理")
@TableName("t_st_crawler_website")
public class StCrawlerWebsite extends IsDeletedYesBaseEntity<StCrawlerWebsite> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "website_id", type = IdType.AUTO)
    private Integer websiteId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "url地址")
    private String url;

}
