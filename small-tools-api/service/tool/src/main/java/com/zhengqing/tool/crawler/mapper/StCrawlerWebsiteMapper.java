package com.zhengqing.tool.crawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.crawler.entity.StCrawlerWebsite;
import com.zhengqing.tool.crawler.model.dto.StCrawlerWebsiteListDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerWebsiteListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小工具 - 爬虫 - 网站管理 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:19:19
 */
public interface StCrawlerWebsiteMapper extends BaseMapper<StCrawlerWebsite> {

    /**
     * 列表分页
     *
     * @param page:
     * @param filter:
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerWebsiteVO>
     * @author zhengqingya
     * @date 2020/8/22 17:21
     */
    IPage<StCrawlerWebsiteListVO> selectStCrawlerWebsites(IPage page, @Param("filter") StCrawlerWebsiteListDTO filter);

    /**
     * 列表
     *
     * @param filter:
     * @return: java.util.List<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerWebsiteVO>
     * @author zhengqingya
     * @date 2020/8/30 14:38
     */
    List<StCrawlerWebsiteListVO> selectStCrawlerWebsites(@Param("filter") StCrawlerWebsiteListDTO filter);

    /**
     * 根据网站id设置网站为无效
     *
     * @param websiteId: 网站id
     * @return: void
     * @author zhengqingya
     * @date 2020/8/22 17:21
     */
    void updateWebsiteInvalid(@Param("websiteId") Integer websiteId);

}
