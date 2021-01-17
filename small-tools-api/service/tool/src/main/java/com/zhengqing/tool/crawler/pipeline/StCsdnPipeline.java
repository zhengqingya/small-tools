package com.zhengqing.tool.crawler.pipeline;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.DateTimeUtil;
import com.zhengqing.tool.crawler.entity.StCrawlerArticleInfo;
import com.zhengqing.tool.crawler.model.bo.StCrawlerCsdnBO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoQueryDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteListDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerArticleInfoListVO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerWebsiteListVO;
import com.zhengqing.tool.crawler.service.IStCrawlerArticleInfoService;
import com.zhengqing.tool.crawler.service.IStCrawlerWebsiteService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * <p>
 * 使用Pipeline保存结果到数据库
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/8/21 23:19
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class StCsdnPipeline implements Pipeline {

    @Autowired
    private IStCrawlerWebsiteService stCrawlerWebsiteService;

    @Autowired
    private IStCrawlerArticleInfoService stCrawlerArticleInfoService;

    // ResultItems保存了抽取结果，它是一个Map结构，
    // 在page.putField(key,value)中保存的数据，可以通过ResultItems.get(key)获取
    @Override
    public void process(ResultItems resultItems, Task task) {
        StCrawlerCsdnBO stCrawlerCsdnBO = resultItems.get("csdn:article:info");
        List<StCrawlerWebsiteListVO> list = stCrawlerWebsiteService.list(StCrawlerWebsiteListDTO.builder().build());
        if (stCrawlerCsdnBO != null) {
            list.forEach(e -> {
                String url = e.getUrl();
                // 获取到博主账号
                String blogAccount = "";
                if (url.startsWith(AppConstant.CSDN_DOMAIN_PREFIX)) {
                    blogAccount = url.split(AppConstant.SEPARATOR_SPRIT)[3];
                } else {
                    blogAccount = url.split(AppConstant.SEPARATOR_SPRIT)[2];
                }
                // 网站id
                Integer websiteId = e.getWebsiteId();
                if (blogAccount.equals(stCrawlerCsdnBO.getBlogAccount())) {
                    StCrawlerArticleInfo stCrawlerArticleInfo = new StCrawlerArticleInfo();
                    stCrawlerArticleInfo.setWebsiteId(websiteId);
                    int articleId = stCrawlerCsdnBO.getId();
                    stCrawlerArticleInfo.setArticleId(articleId);
                    stCrawlerArticleInfo.setTitle(stCrawlerCsdnBO.getTitle());
                    stCrawlerArticleInfo.setCategory(stCrawlerCsdnBO.getCategory().trim());
                    stCrawlerArticleInfo.setContent(stCrawlerCsdnBO.getContent());
                    stCrawlerArticleInfo.setUrl(stCrawlerCsdnBO.getUrl());
                    try {
                        Date publishTime =
                            DateTimeUtil.dateParse(stCrawlerCsdnBO.getTime(), DateTimeUtil.DATE_TIME_PATTERN);
                        stCrawlerArticleInfo.setPublishTime(publishTime);
                        // 这里判断是否已经有解析过的数据，如果有走更新数据，否则走插入
                        // Integer articleInfoId = stCrawlerArticleInfoService.getArticleInfoId(articleId);
                        StCrawlerArticleInfoListVO articleDetail =
                            stCrawlerArticleInfoService.getArticleDetail(StCrawlerArticleInfoQueryDTO.builder()
                                .websiteId(websiteId).title(stCrawlerArticleInfo.getTitle())
                                .category(stCrawlerArticleInfo.getCategory()).build());
                        if (articleDetail == null) {
                            stCrawlerArticleInfoService.save(stCrawlerArticleInfo);
                        } else {
                            stCrawlerArticleInfo.setArticleInfoId(articleDetail.getArticleInfoId());
                            stCrawlerArticleInfoService.updateById(stCrawlerArticleInfo);
                        }
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
            });
        }
    }

}
