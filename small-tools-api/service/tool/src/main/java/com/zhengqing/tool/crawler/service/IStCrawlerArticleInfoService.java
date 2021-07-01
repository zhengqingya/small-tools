package com.zhengqing.tool.crawler.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.crawler.entity.StCrawlerArticleInfo;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoExportDataDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoListDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoQueryDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerArticleInfoListVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息 服务类
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:35:34
 */
public interface IStCrawlerArticleInfoService extends IService<StCrawlerArticleInfo> {

    /**
     * 小工具 - 爬虫 - 文章信息列表分页
     *
     * @param params
     * @return
     */
    IPage<StCrawlerArticleInfoListVO> listPage(StCrawlerArticleInfoListDTO params);

    /**
     * 根据csdn文章id查询是否已存在该数据,存在的话返回文章信息主键id
     *
     * @param articleId: csdn 文章id
     * @return: java.lang.Integer
     * @author zhengqingya
     * @date 2020/8/22 11:48
     */
    Integer getArticleInfoId(Integer articleId);

    /**
     * 查询文章详情数据 - 暂时用于内部查询文章是否存在
     *
     * @param params: 查询参数
     * @return: com.zhengqing.modules.smalltools.crawler.entity.StCrawlerArticleInfo
     * @author zhengqingya
     * @date 2020/9/5 23:12
     */
    StCrawlerArticleInfoListVO getArticleDetail(StCrawlerArticleInfoQueryDTO params);

    /**
     * 根据网站id查询其下的文章总数
     *
     * @param websiteId: 网站id
     * @return: java.lang.Integer
     * @author zhengqingya
     * @date 2020/8/22 12:03
     */
    Integer getArticleSumByWebsiteId(Integer websiteId);

    /**
     * 根据网站id查询其下的文章信息
     *
     * @param websiteId: 网站id
     * @return: java.util.List<com.zhengqing.modules.smalltools.crawler.model.vo.StCrawlerArticleInfoVO>
     * @author zhengqingya
     * @date 2020/8/27 17:56
     */
    List<StCrawlerArticleInfoListVO> getArticlesByWebsiteId(Integer websiteId);

    /**
     * 根据文章id导出数据
     *
     * @param params: 导出参数
     * @return: 下载地址
     * @author zhengqingya
     * @date 2020/8/30 16:17
     */
    String exportData(StCrawlerArticleInfoExportDataDTO params);

    /**
     * 根据网站id导出数据
     *
     * @param websiteId: 网站id
     * @return: 下载地址
     * @author zhengqingya
     * @date 2020/8/27 16:48
     */
    String exportAllDataByWebsiteId(Integer websiteId);

    /**
     * 根据网站id导出Excel数据
     *
     * @param websiteId: 网站id
     * @return: 下载地址
     * @author zhengqingya
     * @date 2020/9/8 14:49
     */
    String exportExcelByWebsiteId(Integer websiteId);

    /**
     * 导入数据
     *
     * @param file:      zip压缩文件数据
     * @param websiteId: 网站id
     * @return: java.lang.String
     * @author zhengqingya
     * @date 2020/9/4 17:30
     */
    String importData(MultipartFile file, Integer websiteId);

}
