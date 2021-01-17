package com.zhengqing.tool.crawler.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * CSDN博客文章信息类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/7/1$ 16:57$
 */
@Data
@AllArgsConstructor
@ApiModel("CSDN博客文章信息类")
public class StCrawlerCsdnBO {

    @ApiModelProperty(value = "博主账号")
    private String blogAccount;

    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章发布时间")
    private String time;

    @ApiModelProperty(value = "文章所属分类")
    private String category;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章url地址")
    private String url;

}
