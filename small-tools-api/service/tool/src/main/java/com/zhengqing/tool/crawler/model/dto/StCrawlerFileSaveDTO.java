package com.zhengqing.tool.crawler.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件提交参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/4 21:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文件提交参数")
public class StCrawlerFileSaveDTO {

    @ApiModelProperty(value = "文件")
    private MultipartFile file;

    @ApiModelProperty(value = "文件目录")
    private String dir;

}
