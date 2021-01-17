package com.zhengqing.tool.crawler.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 小工具 - 爬虫 - 更新数据请求参数
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-08-21 22:19:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 爬虫 - 更新数据请求参数")
public class StCrawlerWebsiteRefreshDataDTO {

    @ApiModelProperty(value = "网站地址")
    private String url;

}
