package com.zhengqing.tool.crawler.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteListDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteRefreshDataDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteSaveDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerWebsiteListVO;
import com.zhengqing.tool.crawler.service.IStCrawlerWebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-08-21 22:19:19
 */
@Slf4j
@RestController
@RequestMapping("/web/api/crawler/website")
@Api(tags = {"小工具 - 爬虫 - 网站管理接口"})
public class StCrawlerWebsiteController extends BaseController {

    @Autowired
    private IStCrawlerWebsiteService stCrawlerWebsiteService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<StCrawlerWebsiteListVO> listPage(@ModelAttribute StCrawlerWebsiteListDTO params) {
        return this.stCrawlerWebsiteService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<StCrawlerWebsiteListVO> list(@ModelAttribute StCrawlerWebsiteListDTO params) {
        return this.stCrawlerWebsiteService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody StCrawlerWebsiteSaveDTO params) {
        return this.stCrawlerWebsiteService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody StCrawlerWebsiteSaveDTO params) {
        return this.stCrawlerWebsiteService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer websiteId) {
        this.stCrawlerWebsiteService.updateWebsiteInvalid(websiteId);
    }

    @NoRepeatSubmit
    @PutMapping("refreshData")
    @ApiOperation("更新网站爬虫数据")
    public void refreshData(@RequestBody StCrawlerWebsiteRefreshDataDTO params) {
        this.stCrawlerWebsiteService.refreshData(params);
    }

}
