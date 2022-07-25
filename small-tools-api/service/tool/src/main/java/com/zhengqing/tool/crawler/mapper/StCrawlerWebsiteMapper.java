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
     * @param page   分页参数
     * @param filter 过滤参数
     * @return 结果
     * @author zhengqingya
     * @date 2020/8/22 17:21
     */
    IPage<StCrawlerWebsiteListVO> selectStCrawlerWebsites(IPage page, @Param("filter") StCrawlerWebsiteListDTO filter);

    /**
     * 列表
     *
     * @param filter 过滤参数
     * @return 结果
     * @author zhengqingya
     * @date 2020/8/30 14:38
     */
    List<StCrawlerWebsiteListVO> selectStCrawlerWebsites(@Param("filter") StCrawlerWebsiteListDTO filter);

}
