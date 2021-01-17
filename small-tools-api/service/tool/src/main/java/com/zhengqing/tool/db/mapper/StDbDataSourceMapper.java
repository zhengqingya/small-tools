package com.zhengqing.tool.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.db.entity.StDbDataSource;
import com.zhengqing.tool.db.model.dto.StDbDataSourceListDTO;
import com.zhengqing.tool.db.model.vo.StDbDataSourceListVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表 Mapper 接口
 * </p>
 *
 * @author : zhengqing
 * @description:
 * @date : 2020-09-02 14:45:55
 */
public interface StDbDataSourceMapper extends BaseMapper<StDbDataSource> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    IPage<StDbDataSourceListVO> selectStDbDataSources(IPage page, @Param("filter") StDbDataSourceListDTO filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<StDbDataSourceListVO> selectStDbDataSources(@Param("filter") StDbDataSourceListDTO filter);

    /**
     * 详情
     *
     * @param id:
     * @return: com.zhengqing.modules.smalltools.db.model.vo.StDbDataSourceVO
     * @author : zhengqing
     * @date : 2020/9/4 14:00
     */
    StDbDataSourceListVO detail(@Param("id") Integer id);

}
