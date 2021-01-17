package com.zhengqing.tool.crawler.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.api.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 小工具 - 爬虫 - 测试 接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/10/31 20:06
 */
@Slf4j
@RestController
@RequestMapping("/web/api/crawler/test")
@Api(tags = {"小工具 - 爬虫 - 测试 接口"})
public class StCrawlerTestController extends BaseController {

    @GetMapping("crawl")
    @ApiOperation("爬虫")
    public void crawl(String url) {

    }

}
