package com.zhengqing.tool.crawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.crawler.entity.StCrawlerArticleInfo;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoListDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoQueryDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerArticleInfoListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:35:34
 */
public interface StCrawlerArticleInfoMapper extends BaseMapper<StCrawlerArticleInfo> {

    /**
     * 列表分页
     *
     * @param page:
     * @param filter:
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerArticleInfoVO>
     * @author zhengqingya
     * @date 2020/8/22 11:33
     */
    IPage<StCrawlerArticleInfoListVO> selectStCrawlerArticleInfos(IPage page,
                                                                  @Param("filter") StCrawlerArticleInfoListDTO filter);

    /**
     * 根据csdn文章id查询是否已存在该数据,存在的话返回文章信息主键id
     *
     * @param articleId: csdn 文章id
     * @return java.lang.Integer
     * @author zhengqingya
     * @date 2020/8/22 11:44
     */
    Integer selectArticleInfoId(@Param("articleId") Integer articleId);

    /**
     * 根据网站id查询其下的文章总数
     *
     * @param websiteId: 网站id
     * @return java.lang.Integer
     * @author zhengqingya
     * @date 2020/8/22 11:58
     */
    Integer selectArticleSumByWebsiteId(@Param("websiteId") Integer websiteId);

    /**
     * 根据网站id查询其下的文章信息
     *
     * @param websiteId: 网站id
     * @return java.util.List<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerArticleInfoVO>
     * @author zhengqingya
     * @date 2020/8/27 17:56
     */
    List<StCrawlerArticleInfoListVO> selectArticlesByWebsiteId(@Param("websiteId") Integer websiteId);

    /**
     * 查询文章详情数据
     *
     * @param filter: 查询参数
     * @return com.zhengqing.modules.smalltools.crawler.entity.StCrawlerArticleInfo
     * @author zhengqingya
     * @date 2020/9/5 23:12
     */
    StCrawlerArticleInfoListVO selectArticleDetail(@Param("filter") StCrawlerArticleInfoQueryDTO filter);

}
