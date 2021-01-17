package com.zhengqing.tool.crawler.model.dto;

import com.zhengqing.common.exception.ParameterException;
import com.zhengqing.common.parameter.ParameterVerify;
import com.zhengqing.common.util.MyDateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息查询参数
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-08-21 22:35:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 爬虫 - 文章信息查询参数")
public class StCrawlerArticleInfoListDTO implements ParameterVerify {

    @ApiModelProperty(value = "网站id")
    private Integer websiteId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @Override
    public void checkParam() throws ParameterException {
        // 校验结束时间是否大于等于开始时间
        if (StringUtils.isNotBlank(this.startTime) && StringUtils.isNotBlank(this.endTime)
            && !MyDateUtil.verifyTime(this.startTime, this.endTime, MyDateUtil.DATE_TIME_FORMAT)) {
            throw new ParameterException("结束时间不能小于开始时间!");
        }
    }

}
