package com.zhengqing.tool.crawler.model.dto;

import com.zhengqing.common.validator.fieldrepeat.FieldRepeatValidator;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理保存参数
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
@ApiModel("小工具 - 爬虫 - 网站管理保存参数")
@FieldRepeatValidator(tableName = "t_st_crawler_website", idDbName = "website_id", fieldNames = {"name"},
        dbFieldNames = {"name"}, message = "网站名称重复，请重新输入！")
public class StCrawlerWebsiteSaveDTO {

    @NotNull(message = "网站ID不能为空！", groups = {UpdateGroup.class})
    @ApiModelProperty(value = "网站ID")
    private Integer websiteId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "url地址")
    private String url;

}
