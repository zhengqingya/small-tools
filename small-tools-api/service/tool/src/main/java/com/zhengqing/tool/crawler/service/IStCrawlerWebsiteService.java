package com.zhengqing.tool.crawler.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.crawler.entity.StCrawlerWebsite;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteListDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteRefreshDataDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteSaveDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerWebsiteListVO;

import java.util.List;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:19:19
 */
public interface IStCrawlerWebsiteService extends IService<StCrawlerWebsite> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerWebsiteVO>
     * @author zhengqingya
     * @date 2020/8/21 22:48
     */
    IPage<StCrawlerWebsiteListVO> listPage(StCrawlerWebsiteListDTO params);

    /**
     * 列表
     *
     * @param params: 查询参数
     * @return java.util.List<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerWebsiteVO>
     * @author zhengqingya
     * @date 2020/8/30 14:37
     */
    List<StCrawlerWebsiteListVO> list(StCrawlerWebsiteListDTO params);

    /**
     * 更新网站爬虫数据
     *
     * @param params:
     * @return void
     * @author zhengqingya
     * @date 2020/8/21 23:26
     */
    void refreshData(StCrawlerWebsiteRefreshDataDTO params);

    /**
     * 新增或修改网站
     *
     * @param params: 提交参数
     * @return 网站id
     * @author zhengqingya
     * @date 2020/8/22 17:12
     */
    Integer addOrUpdateData(StCrawlerWebsiteSaveDTO params);

    /**
     * 根据网站id设置网站为无效
     *
     * @param websiteId: 网站id
     * @return void
     * @author zhengqingya
     * @date 2020/8/22 17:20
     */
    void updateWebsiteInvalid(Integer websiteId);

}
