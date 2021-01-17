package com.zhengqing.tool.crawler.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoExportDataDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoListDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerArticleInfoListVO;
import com.zhengqing.tool.crawler.service.IStCrawlerArticleInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息 接口
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-08-21 22:35:34
 *
 */
@Slf4j
@RestController
@RequestMapping("/web/api/crawler/articleInfo")
@Api(tags = {"小工具 - 爬虫 - 文章信息接口"})
public class StCrawlerArticleInfoController extends BaseController {

    @Autowired
    private IStCrawlerArticleInfoService stCrawlerArticleInfoService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<StCrawlerArticleInfoListVO> listPage(@ModelAttribute StCrawlerArticleInfoListDTO params) {
        return stCrawlerArticleInfoService.listPage(params);
    }

    @NoRepeatSubmit
    @GetMapping("exportData")
    @ApiOperation("导出数据(html/md/word...)")
    public String exportData(@Validated @ModelAttribute StCrawlerArticleInfoExportDataDTO params) {
        return stCrawlerArticleInfoService.exportData(params);
    }

    @NoRepeatSubmit
    @GetMapping("exportAllDataByWebsiteId")
    @ApiOperation("根据网站导出所有爬虫文章数据")
    public String exportAllDataByWebsiteId(@RequestParam Integer websiteId) {
        return stCrawlerArticleInfoService.exportAllDataByWebsiteId(websiteId);
    }

    @NoRepeatSubmit
    @GetMapping("exportExcelByWebsiteId")
    @ApiOperation("根据网站导出爬虫文章数据Excel")
    public String exportExcelByWebsiteId(@RequestParam Integer websiteId) {
        return stCrawlerArticleInfoService.exportExcelByWebsiteId(websiteId);
    }

    @NoRepeatSubmit
    @PostMapping("importData")
    @ApiOperation("导入数据")
    public String importData(@RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam(value = "websiteId", required = false) Integer websiteId, HttpServletRequest request) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile fileNew = multipartRequest.getFile("file");
        String websiteIdStr = multipartRequest.getParameter("websiteId");
        Integer websiteIdNew = null;
        if (StringUtils.isNotBlank(websiteIdStr)) {
            websiteIdNew = Integer.valueOf(websiteIdStr);
        }
        return stCrawlerArticleInfoService.importData(fileNew, websiteIdNew);
    }

}
