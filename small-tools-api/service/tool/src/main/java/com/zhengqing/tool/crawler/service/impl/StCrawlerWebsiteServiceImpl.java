package com.zhengqing.tool.crawler.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.tool.crawler.entity.StCrawlerWebsite;
import com.zhengqing.tool.crawler.mapper.StCrawlerWebsiteMapper;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteListDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteRefreshDataDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteSaveDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerWebsiteListVO;
import com.zhengqing.tool.crawler.pipeline.StCsdnPipeline;
import com.zhengqing.tool.crawler.processor.StCsdnPageProcessor;
import com.zhengqing.tool.crawler.service.IStCrawlerArticleInfoService;
import com.zhengqing.tool.crawler.service.IStCrawlerWebsiteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:19:19
 */
// @DS(Constants.DATASOURCE_TEST)
@Service
@Transactional(rollbackFor = Exception.class)
public class StCrawlerWebsiteServiceImpl extends ServiceImpl<StCrawlerWebsiteMapper, StCrawlerWebsite>
        implements IStCrawlerWebsiteService {

    @Resource
    private StCrawlerWebsiteMapper stCrawlerWebsiteMapper;

    @Resource
    private IStCrawlerArticleInfoService stCrawlerArticleInfoService;

    @Resource
    private StCsdnPipeline stCsdnPipeline;

    @Override
    public IPage<StCrawlerWebsiteListVO> listPage(StCrawlerWebsiteListDTO params) {
        IPage<StCrawlerWebsiteListVO> result = this.stCrawlerWebsiteMapper.selectStCrawlerWebsites(new Page<>(), params);
        result.getRecords()
                .forEach(e -> e.setArticleSum(this.stCrawlerArticleInfoService.getArticleSumByWebsiteId(e.getWebsiteId())));
        return result;
    }

    @Override
    public List<StCrawlerWebsiteListVO> list(StCrawlerWebsiteListDTO params) {
        List<StCrawlerWebsiteListVO> result = this.stCrawlerWebsiteMapper.selectStCrawlerWebsites(params);
        result.forEach(e -> e.setArticleSum(this.stCrawlerArticleInfoService.getArticleSumByWebsiteId(e.getWebsiteId())));
        return result;
    }

    @Override
    public void refreshData(StCrawlerWebsiteRefreshDataDTO params) {
        String url = params.getUrl();
        Spider.create(new StCsdnPageProcessor())
                // 使用Pipeline保存结果
                .addPipeline(this.stCsdnPipeline)
                // 从指定的url地址开始抓
                .addUrl(url)
                // 开启5个线程抓取
                .thread(5)
                // 启动爬虫
                .run();
    }

    @Override
    public Integer addOrUpdateData(StCrawlerWebsiteSaveDTO params) {
        Integer websiteId = params.getWebsiteId();
        StCrawlerWebsite website = MyBeanUtil.copyProperties(params, StCrawlerWebsite.class);
        if (websiteId == null) {
            this.stCrawlerWebsiteMapper.insert(website);
        } else {
            this.stCrawlerWebsiteMapper.updateById(website);
        }
        return website.getWebsiteId();
    }

    @Override
    public void deleteData(Integer websiteId) {
        this.stCrawlerWebsiteMapper.deleteById(websiteId);
    }

}
