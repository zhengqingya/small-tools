package com.zhengqing.tool.crawler.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-08-21 22:19:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 爬虫 - 网站管理查询参数")
public class StCrawlerWebsiteListDTO {

    @ApiModelProperty(value = "名称")
    private String name;

}
